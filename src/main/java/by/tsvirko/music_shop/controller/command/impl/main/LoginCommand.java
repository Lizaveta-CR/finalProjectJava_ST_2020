package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.Menu;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginCommand extends Command {
    //TODO: check enabled
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static Map<Role, List<Menu>> menu = new ConcurrentHashMap<>();

    //
    static {
//        TODO:add i18n
//        menu.put(Role.BUYER, "/buyer/buyerForm.jsp");
        menu.put(Role.BUYER, Arrays.asList(new Menu(PathConstnant.BUYER_FORM, "app.menu.myPage")));
        menu.put(Role.ADMINISTRATOR, Arrays.asList(new Menu(PathConstnant.ADMIN_FORM, "app.menu.myPage")
                , new Menu(PathConstnant.ADMIN_BUYERS, "app.menu.buyers")
                , new Menu(PathConstnant.PRODUCTS_UNAVAILABLE, "app.menu.unavailableProducts")

        ));
//        menu.put(Role.MANAGER, "/manager/managerForm.jsp");
    }

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("/login", true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (!login.isEmpty() && !password.isEmpty()) {
            UserService service = null;
            try {
                service = factory.getService(UserService.class);
                User user = service.findByLoginAndPassword(login, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(AttributeConstant.AUTHORIZED_USER.value(), user);

                    if (user.getRole().equals(Role.BUYER)) {
                        try {
                            BuyerService buyerService = factory.getService(BuyerService.class);
                            Buyer buyer = buyerService.findById(user.getId());
                            if (buyer.isEnabled()) {
                                session.setAttribute(AttributeConstant.AUTHORIZED_BUYER.value(), buyer);
                            } else {
                                logger.info(String.format("buyer \"%s\" is not enabled to access resource", login));
                                forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(),
                                        rb.getString("app.message.login.enabledError"));
                                session.removeAttribute(AttributeConstant.AUTHORIZED_USER.value());
                                return forward;
                            }
                        } catch (ServicePersistentException e) {
                        }
                    }
                    logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", login,
                            request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                    session.setAttribute(AttributeConstant.MENU.value(), menu.get(user.getRole()));
                    forward.setForward("/index");
                    return forward;
                }
            } catch (ServicePersistentException e) {
                request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.login.notRecognized"));
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return null;
            }
        }
        request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.login.empty"));
        return null;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
