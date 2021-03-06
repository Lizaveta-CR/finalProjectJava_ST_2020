package by.tsvirko.music_shop.controller.filter;

import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.impl.common.MainCommand;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Security filter for rejecting unforeseen interference with application roles.
 */
public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * Prevents unforeseen interference to commands.
     *
     * @param servletRequest  -  the ServletRequest object, contains the client's request
     * @param servletResponse - the ServletResponse object, contains the filter's response
     * @param filterChain     - the FilterChain for invoking the next filter or the resource
     * @throws IOException      -  if an I/O related error has occurred during the processing
     * @throws ServletException - if servlet exception occurs
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            Command command = (Command) httpRequest.getAttribute(AttributeConstant.COMMAND.value());
            Set<Role> allowRoles = command.getAllowRoles();

            HttpSession session = httpRequest.getSession(false);
            User user = null;
            if (session != null) {
                user = (User) session.getAttribute(AttributeConstant.AUTHORIZED_USER.value());
                String errorMessage = (String) session.getAttribute(AttributeConstant.SECURITY_FILTER_MESSAGE.value());
                if (errorMessage != null) {
                    httpRequest.setAttribute(AttributeConstant.MESSAGE.value(), errorMessage);
                    session.removeAttribute(AttributeConstant.SECURITY_FILTER_MESSAGE.value());
                }
            }
            boolean canExecute = allowRoles == null;

            if (user != null) {
                canExecute = canExecute || allowRoles.contains(user.getRole());
            }

            if (canExecute) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.info(String.format("Trying of %s access to forbidden resource", command.getName()));
                if (session != null && command.getClass() != MainCommand.class) {
                    ResourceBundle rb = ResourceBundleUtil.getResourceBundle(httpRequest);
                    session.setAttribute(AttributeConstant.SECURITY_FILTER_MESSAGE.value(), rb.getString("app.message.security"));
                }
                httpResponse.sendRedirect(httpRequest.getContextPath() + PathConstant.LOGIN);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            servletRequest.getServletContext().getRequestDispatcher(PathConstant.ERROR_PAGES_LOCATION).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
