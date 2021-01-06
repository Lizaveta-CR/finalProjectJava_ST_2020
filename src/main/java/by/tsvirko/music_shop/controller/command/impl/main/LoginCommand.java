package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoginCommand extends Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static Map<Role, String> menu = new ConcurrentHashMap<>();

    //
    static {
        menu.put(Role.BUYER, "/buyer/buyerForm.jsp");
        menu.put(Role.ADMINISTRATOR, "/admin/adminForm.jsp");
        menu.put(Role.MANAGER, "/manager/managerForm.jsp");
    }

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
//        Forward forward = new Forward("/login", true);
        Forward forward = new Forward("/login", true);

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
//                    session.setAttribute("menu", menu.get(user.getRole()));
                    logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", login,
                            request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                    forward.setForward(menu.get(user.getRole()));
                    forward.setRedirect(false);
                    return forward;
                }
            } catch (ServicePersistentException e) {
//                forward.getAttributes().put("message", "Not recognized");
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
//                request.setAttribute("redirectedData", "Not recognized");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return null;
            }
        }
        //TODO: return null как у нее в проекте
//        forward.getAttributes().put("message", "Not empty fields!");
        request.setAttribute("message", "Not empty fields!");
        return null;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
