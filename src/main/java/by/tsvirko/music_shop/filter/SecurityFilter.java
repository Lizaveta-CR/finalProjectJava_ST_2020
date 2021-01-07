package by.tsvirko.music_shop.filter;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.impl.main.MainCommand;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            Command command = (Command) httpRequest.getAttribute("command");
            Set<Role> allowRoles = command.getAllowRoles();

            HttpSession session = httpRequest.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute("authorizedUser");
                String errorMessage = (String) session.getAttribute("securityFilterMessage");
                if (errorMessage != null) {
                    httpRequest.setAttribute("message", errorMessage);
                    session.removeAttribute("securityFilterMessage");
                }

            }
            boolean canExecute = allowRoles == null;

            if (user != null) {
                canExecute = canExecute || allowRoles.contains(user.getRole());
            }

            if (canExecute) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.info(String.format("Trying of %s access to forbidden resource", user.getLogin()));
                if (session != null && command.getClass() != MainCommand.class) {
                    ResourceBundle rb = ResourceBundleUtil.getResourceBundle(httpRequest);
                    session.setAttribute("securityFilterMessage", rb.getString("app.message.security"));
                }
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            servletRequest.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
