package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand extends Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
//    private static Map<Role, List<String>> menu = new ConcurrentHashMap<>();

//    static {
//        menu.put(Role.BUYER, )
//    }

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("jsp/pages/login.jsp", true);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (!login.isEmpty() && !password.isEmpty()) {
            UserService service = null;
            try {
                service = factory.getService(UserService.class);
                User user = service.findByLoginAndPassword(login, password);
                if (user != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("authorizedUser", user.getLogin());
//                    session.setAttribute("menu", menu.get(user.getRole()));
                    logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", login,
                            request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                    forward.setForward("/index.jsp");
                    return forward;
                }
            } catch (ServicePersistentException e) {
                forward.getAttributes().put("message", "Not recognized");
//                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
//                request.setAttribute("redirectedData", "Not recognized");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)",
                        login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return forward;
            }
        }
        forward.getAttributes().put("message", "Not empty fields!");
//        request.setAttribute("message", "Not empty fields!");
        return forward;
    }
}
