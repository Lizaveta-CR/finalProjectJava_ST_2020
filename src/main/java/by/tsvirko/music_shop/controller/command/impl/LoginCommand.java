package by.tsvirko.music_shop.controller.command.impl;

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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginCommand extends Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
//    private static Map<Role, List<String>> menu = new ConcurrentHashMap<>();

//    static {
//        menu.put(Role.BUYER, )
//    }

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login != null && password != null) {
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
                    return new Forward("/index.jsp");
                } else {
                    //TODO: не передается!
//                    request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                    request.setAttribute("message", "Not recognized");
                    logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)",
                            login, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                }
            } catch (ServicePersistentException e) {
                logger.error("Service can not be instantiated");
            }
        }
        return new Forward("/login.jsp", false);
    }
}
