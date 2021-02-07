package by.tsvirko.music_shop.controller.filter;

import by.tsvirko.music_shop.controller.command.CommandFactory;
import by.tsvirko.music_shop.controller.command.CommandName;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Filter to delegate commands
 */
public class CommandFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CommandFilter.class);

    private static Map<String, CommandName> getCommands = new ConcurrentHashMap<>();
    private static Map<String, CommandName> postCommands = new ConcurrentHashMap<>();
    private CommandFactory factory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        factory = CommandFactory.getInstance();
    }

    static {
        getCommands.put("/", CommandName.MAIN_COMMAND);
        getCommands.put("/index", CommandName.MAIN_COMMAND);
        getCommands.put("/welcome", CommandName.MAIN_COMMAND);

        getCommands.put("/changeLocale", CommandName.CHANGE_LANGUAGE_COMMAND);
        getCommands.put("/login", CommandName.VIEW_LOGIN_COMMAND);
        getCommands.put("/logout", CommandName.LOGOUT_COMMAND);
        getCommands.put("/registration", CommandName.VIEW_REGISTER_COMMAND);
        getCommands.put("/about", CommandName.ABOUT_VIEW_COMMAND);

        getCommands.put("/products/list", CommandName.CATEGORIES_COMMAND);
        getCommands.put("/products/edit", CommandName.ADMIN_EDIT_PRODUCTS_VIEW_COMMAND);
        getCommands.put("/products/producer", CommandName.SHOW_PRODUCER_PRODUCTS_COMMAND);

        getCommands.put("/admin/buyers", CommandName.ADMIN_BUYERS_COMMAND);
        getCommands.put("/admin/unavailableProducts", CommandName.SHOW_UNAVAILABLE_PRODUCTS_COMMAND);
        getCommands.put("/admin/winner", CommandName.SHOW_WINNER_COMMAND);

        getCommands.put("/buyer/buyerForm", CommandName.BUYER_FORM_COMMAND);
        getCommands.put("/buyer/feedBack", CommandName.VIEW_FEEDBACK_PAGE);
        getCommands.put("/buyer/edit", CommandName.BUYER_VIEW_EDIT_FORM_COMMAND);
        getCommands.put("/buyer/editPass", CommandName.BUYER_VIEW_EDIT_PASS_FORM_COMMAND);
        getCommands.put("/buyer/address", CommandName.VIEW_ADDRESS_COMMAND);
        getCommands.put("/buyer/order", CommandName.VIEW_ORDER_COMMAND);
        getCommands.put("/buyer/order/submit", CommandName.BUYER_VIEW_SUBMIT_ORDER_COMMAND);

        getCommands.put("/manag/earnings", CommandName.VIEW_EARNINGS_COMMAND);
        getCommands.put("/manag/personal", CommandName.VIEW_PERSONAL_COMMAND);
        getCommands.put("/manag/add-personal", CommandName.VIEW_ADD_PERSONAL_COMMAND);
        getCommands.put("/products/create", CommandName.VIEW_ADD_PRODUCT_COMMAND);

        postCommands.put("/login", CommandName.LOGIN_COMMAND);
        postCommands.put("/registration", CommandName.REGISTER_COMMAND);

        postCommands.put("/buyer/edit", CommandName.BUYER_EDIT_COMMAND);
        postCommands.put("/buyer/editPass", CommandName.BUYER_EDIT_PASS_COMMAND);
        postCommands.put("/buyer/address", CommandName.EDIT_ADDRESS_COMMAND);
        postCommands.put("/buyer/order/remove", CommandName.BUYER_REMOVE_PRODUCT_COMMAND);
        postCommands.put("/buyer/order/submit", CommandName.SUBMIT_ORDER_COMMAND);
        postCommands.put("/buyer/buyerForm", CommandName.BUYER_FORM_COMMAND);
        postCommands.put("/buyer/feedBack/submit", CommandName.SUBMIT_FEEDBACK_COMMAND);
        postCommands.put("/products/buy", CommandName.BUYER_ADD_PRODUCT_COMMAND);
        postCommands.put("/products/create", CommandName.ADD_PRODUCT_COMMAND);
        postCommands.put("/products/delete", CommandName.DELETE_PRODUCT_COMMAND);
        postCommands.put("/products/byRate", CommandName.BUYER_PRODUCTS_BY_RATE_COMMAND);

        postCommands.put("/admin/buyers", CommandName.ADMIN_BUYERS_COMMAND);
        postCommands.put("/admin/buyers/disable", CommandName.DISABLE_ACCESS_COMMAND);
        postCommands.put("/admin/buyers/enable", CommandName.ENABLE_ACCESS_COMMAND);
        postCommands.put("/admin/unavailableProducts", CommandName.ENABLE_PRODUCT_COMMAND);
        postCommands.put("/admin/generate", CommandName.GENERATE_BUYER_COMMAND);
        postCommands.put("/admin/mail", CommandName.ADMIN_SEND_MAIL_COMMAND);
        postCommands.put("/products/edit", CommandName.ADMIN_EDIT_PRODUCTS_COMMAND);

        postCommands.put("/manag/add-personal", CommandName.ADD_EMPLOYEE_COMMAND);
        postCommands.put("/manag/delete", CommandName.DELETE_EMPLOYEE_COMMAND);
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

            CommandName commandName = null;
            switch (httpRequest.getMethod().toUpperCase()) {
                case "GET":
                    commandName = getCommands.get(actionName);
                    break;
                case "POST":
                    commandName = postCommands.get(actionName);
                    break;
            }

            try {
                Command command = factory.getCommand(commandName);
                httpRequest.setAttribute(AttributeConstant.COMMAND.value(), command);
                command.setName(actionName);
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (CommandException e) {
                logger.error("It is impossible to create command handler object and use filter");
                ResourceBundle rb = ResourceBundleUtil.getResourceBundle(httpRequest);
                String errMes = rb.getString("app.error.requested") + uri + rb.getString("app.error.requested.not.found");
                httpRequest.setAttribute(AttributeConstant.ERROR.value(), errMes);
                httpRequest.getServletContext().getRequestDispatcher(PathConstant.ERROR_PAGES_LOCATION).forward(servletRequest, servletResponse);
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
