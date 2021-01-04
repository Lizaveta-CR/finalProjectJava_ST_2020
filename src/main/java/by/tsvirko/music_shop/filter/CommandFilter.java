package by.tsvirko.music_shop.filter;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.impl.*;
import by.tsvirko.music_shop.controller.command.impl.main.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Filter to delegate commands
 */
public class CommandFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CommandFilter.class);


    private static Map<String, Command> getCommands = new ConcurrentHashMap<>();
    private static Map<String, Command> postCommands = new ConcurrentHashMap<>();

    static {
        getCommands.put("/finalProject/", new MainCommand());
        getCommands.put("/index", new MainCommand());
        getCommands.put("/login", new ViewLoginCommand());
        getCommands.put("/logout", new LogoutCommand());
        getCommands.put("/registration", new ViewRegisterCommand());
        getCommands.put("/products/list", new CategoriesCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/registration", new RegisterCommand());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

            String contextPath = httpRequest.getContextPath();
            String uri = httpRequest.getRequestURI();
            logger.debug(String.format("Starting of processing of request for URI \"%s\"", uri));

            int beginAction = contextPath.length();
            int endAction = uri.lastIndexOf('.');
            String actionName;
            if (endAction >= 0) {
                actionName = uri.substring(beginAction, endAction);
            } else {
                actionName = uri.substring(beginAction);
            }
            Command command = null;
            switch (httpRequest.getMethod().toUpperCase()) {
                case "GET":
                    command = getCommands.get(actionName);
                    break;
                case "POST":
                    command = postCommands.get(actionName);
                    break;
            }
            if (command != null) {
                httpRequest.setAttribute("command", command);
            } else {
                logger.error("It is impossible to create command handler object and use filter");
//                servletRequest.getServletContext().getRequestDispatcher("/WEB-INF/error.jsp");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
