package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.model.Menu;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Command for login into system
 */
public class LoginCommand extends Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static Map<Role, List<Menu>> menu = new HashMap<>();
    private static final String SUFFIX = ".jsp";

    static {
        menu.put(Role.BUYER, Arrays.asList(new Menu(PathConstant.BUYER_FORM, "app.menu.myPage")));
        menu.put(Role.ADMINISTRATOR
                , Arrays.asList(
                        new Menu(PathConstant.ADMIN_BUYERS, "app.menu.buyers")
                        , new Menu(PathConstant.PRODUCTS_UNAVAILABLE, "app.menu.unavailableProducts")));
        menu.put(Role.MANAGER, Arrays.asList(
                new Menu(PathConstant.MANAG_EARNINGS, "app.menu.earnings"),
                new Menu(PathConstant.MANAGER_PERSONAL, "app.menu.personal")));
    }

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.LOGIN, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        String login = request.getParameter(ParameterConstant.LOGIN.value());
        String password = request.getParameter(ParameterConstant.PASS.value());
        if (!login.isEmpty() && !password.isEmpty()) {
            UserService service = null;
            try {
                service = factory.getService(ServiceType.USER);
                User user = service.findByLoginAndPassword(login, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute(AttributeConstant.AUTHORIZED_USER.value(), user);

                    if (user.getRole().equals(Role.BUYER)) {
                        try {
                            BuyerService buyerService = factory.getService(ServiceType.BUYER);
                            Buyer buyer = buyerService.findById(user.getId());
                            if (buyer.isEnabled()) {
                                session.setAttribute(AttributeConstant.AUTHORIZED_BUYER.value(), buyer);
                            } else {
                                logger.info(String.format("buyer \"%s\" is not enabled to access resource", login));
                                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(),
                                        rb.getString("app.message.login.enabledError"));
                                session.removeAttribute(AttributeConstant.AUTHORIZED_USER.value());
                                return responseEntity;
                            }
                        } catch (ServicePersistentException e) {
                        }
                    }
                    logger.info(String.format("user %s is logged in from %s (%s:%s)", login,
                            request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                    session.setAttribute(AttributeConstant.MENU.value(), menu.get(user.getRole()));
                    responseEntity.setForward(PathConstant.MAIN);
                    return responseEntity;
                }
            } catch (ServicePersistentException e) {
                request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.login.notRecognized"));
                logger.info(String.format("user %s unsuccessfully tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                String forwardName = getName().concat(SUFFIX);
                responseEntity.setForward(forwardName);
                responseEntity.setRedirect(false);
                return responseEntity;
            }
        }
        request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.login.empty"));
        String forwardName = getName().concat(SUFFIX);
        responseEntity.setForward(forwardName);
        responseEntity.setRedirect(false);
        return responseEntity;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
