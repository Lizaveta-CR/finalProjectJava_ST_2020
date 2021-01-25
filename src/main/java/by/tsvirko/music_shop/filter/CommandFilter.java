package by.tsvirko.music_shop.filter;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.Command;
//import by.tsvirko.music_shop.controller.command.impl.buyer.BuyerEditCommand;
import by.tsvirko.music_shop.controller.command.impl.admin.*;
import by.tsvirko.music_shop.controller.command.impl.buyer.*;
import by.tsvirko.music_shop.controller.command.impl.main.*;
import by.tsvirko.music_shop.controller.command.impl.manager.*;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
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

    private static Map<String, Command> getCommands = new ConcurrentHashMap<>();
    private static Map<String, Command> postCommands = new ConcurrentHashMap<>();

    static {
        getCommands.put("/", new MainCommand());
        getCommands.put("/index", new MainCommand());
        getCommands.put("/welcome", new MainCommand());

        getCommands.put("/changeLocale", new ChangeLanguageCommand());
        getCommands.put("/login", new ViewLoginCommand());
        getCommands.put("/logout", new LogoutCommand());
        getCommands.put("/registration", new ViewRegisterCommand());

        getCommands.put("/products/list", new CategoriesCommand());
        getCommands.put("/products/edit", new AdminEditProductsViewCommand());
        getCommands.put("/products/producer", new ShowProducerCommand());

        getCommands.put("/admin/buyers", new AdminBuyersCommand());
        getCommands.put("/admin/unavailableProducts", new ShowUnavailableProductsCommand());
        getCommands.put("/admin/winner", new ShowWinnerCommand());

        getCommands.put("/buyer/buyerForm", new BuyerFormCommand());
        getCommands.put("/buyer/feedBack", new ViewFeedBackPage());
        getCommands.put("/buyer/edit", new BuyerViewEditFormCommand());
        getCommands.put("/buyer/editPass", new BuyerViewEditPassFormCommand());
        getCommands.put("/buyer/address", new ViewAddressCommand());
        getCommands.put("/buyer/order", new ViewOrderCommand());
        getCommands.put("/buyer/order/submit", new BuyerViewSubmitOrderCommand());

        getCommands.put("/manag/earnings", new ViewEarningsCommand());
        getCommands.put("/manag/personal", new ViewPersonalCommand());
        getCommands.put("/manag/add-personal", new ViewAddPersonalCommand());
        getCommands.put("/products/create", new ViewAddProductCommand());

        postCommands.put("/login", new LoginCommand());
        postCommands.put("/registration", new RegisterCommand());

        postCommands.put("/buyer/edit", new BuyerEditCommand());
        postCommands.put("/buyer/editPass", new BuyerEditPassCommand());
        postCommands.put("/buyer/address", new EditAddressCommand());
        postCommands.put("/buyer/order/remove", new BuyerRemoveProductCommand());
        postCommands.put("/buyer/order/submit", new SubmitOrderCommand());
        postCommands.put("/buyer/buyerForm", new BuyerFormCommand());
        postCommands.put("/buyer/feedBack/submit", new SubmitFeedBackCommand());
        postCommands.put("/products/buy", new BuyerAddProductCommand());
        postCommands.put("/products/create", new AddProductCommand());
        postCommands.put("/products/delete", new DeleteProductCommand());

        postCommands.put("/admin/buyers", new AdminBuyersCommand());
        postCommands.put("/admin/buyers/disable", new DisableAccessCommand());
        postCommands.put("/admin/buyers/enable", new EnableAccessCommand());
        postCommands.put("/admin/unavailableProducts", new EnableProductCommand());
        postCommands.put("/admin/generate", new GenerateBuyerCommand());
        postCommands.put("/admin/mail", new AdminSendMailCommand());
        postCommands.put("/products/edit", new AdminEditProductsCommand());

        postCommands.put("/manag/add-personal", new AddEmployeeCommand());
        postCommands.put("/manag/delete", new DeleteEmployeeCommand());
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
                httpRequest.setAttribute(AttributeConstant.COMMAND.value(), command);
                command.setName(actionName);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                logger.error("It is impossible to create command handler object and use filter");
                ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(httpRequest);
                String errMes = rb.getString("app.error.requested") + uri + rb.getString("app.error.requested.not.found");
                httpRequest.setAttribute(AttributeConstant.ERROR.value(), errMes);
                httpRequest.getServletContext().getRequestDispatcher(PathConstnant.ERROR_PAGES_LOCATION).forward(servletRequest, servletResponse);
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            servletRequest.getServletContext().getRequestDispatcher(PathConstnant.ERROR_PAGES_LOCATION).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
