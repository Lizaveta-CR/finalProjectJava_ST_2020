package by.tsvirko.music_shop.controller.command.impl.main;

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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoginCommand extends Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static Map<Role, Menu> menu = new ConcurrentHashMap<>();

    //
    static {
//        TODO:add i18n
//        menu.put(Role.BUYER, "/buyer/buyerForm.jsp");
        menu.put(Role.BUYER, new Menu("/buyer/buyerForm"));
//        menu.put(Role.ADMINISTRATOR, "/admin/adminForm.jsp");
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
                    //TODO: класть не целого юзера, потом это поменять в security filter
                    session.setAttribute("authorizedUser", user);
                    try {
                        BuyerService buyerService = factory.getService(BuyerService.class);
                        Buyer buyer = buyerService.findById(user.getId());
                        session.setAttribute("authorizedBuyer", buyer);
                    } catch (ServicePersistentException e) {
                    }
//                    session.setAttribute("menu", menu.get(user.getRole()));
                    logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", login,
                            request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
//                    forward.setForward(menu.get(user.getRole()).getUrl());
                    session.setAttribute("menu", menu.get(user.getRole()));
                    forward.setForward("/index");
//                    forward.setRedirect(false);
                    return forward;
                }
            } catch (ServicePersistentException e) {
                request.setAttribute("message", rb.getString("app.message.login.notRecognized"));
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return null;
            }
        }
        request.setAttribute("message", rb.getString("app.message.login.empty"));
        return null;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
