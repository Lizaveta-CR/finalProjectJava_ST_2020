package by.tsvirko.music_shop.controller;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.CommandManager;
import by.tsvirko.music_shop.controller.command.CommandManagerFactory;
import by.tsvirko.music_shop.controller.command.constant.ResourceBundleAttribute;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.dao.pool.ConnectionPool;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Main controlling servlet
 *
 * @author Tsvirko Lizaveta
 */
@MultipartConfig
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    @Override
    public void init() {
        ResourceBundle resource = ResourceBundle.getBundle(ParameterConstant.DATASOURCE_NAME.value());
        String driver = resource.getString(ResourceBundleAttribute.DRIVER.value());
        String url = resource.getString(ResourceBundleAttribute.URL.value());
        String user = resource.getString(ResourceBundleAttribute.USER.value());
        String password = resource.getString(ResourceBundleAttribute.PASSWORD.value());
        int poolSize = Integer.parseInt(resource.getString(ResourceBundleAttribute.POOL_SIZE.value()));
        int maxSize = Integer.parseInt(resource.getString(ResourceBundleAttribute.POOL_MAX_SIZE.value()));
        int checkConnectionTimeout = Integer.parseInt(resource.getString(ResourceBundleAttribute.CONNECTIONS_TIMEOUT.value()));
        try {
            ConnectionPool.getInstance().initPoolData(driver, url, user, password, poolSize, maxSize, checkConnectionTimeout);
        } catch (ConnectionPoolException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    public ServiceFactory getFactory() throws PersistentException {
        return new ServiceFactoryImpl(new TransactionFactoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = (Command) req.getAttribute(AttributeConstant.COMMAND.value());
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>) session.getAttribute(AttributeConstant.REDIRECTED_DATA.value());
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        req.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute(AttributeConstant.REDIRECTED_DATA.value());
                }
            }
            CommandManager actionManager = CommandManagerFactory.getManager(getFactory());
            Command.Forward forward = actionManager.execute(command, req, resp);
            actionManager.close();
            if (session != null && forward != null && !forward.getAttributes().isEmpty()) {
                session.setAttribute(AttributeConstant.REDIRECTED_DATA.value(), forward.getAttributes());
            }
            String requestedUri = req.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String contextPath = req.getContextPath();
                String redirectedUri = contextPath + forward.getForward();
                logger.debug(String.format("Request for URI %s id redirected to URI %s", requestedUri, redirectedUri));
                resp.sendRedirect(redirectedUri);
            } else {
                String jspPage = null;
                if (forward != null) {
                    jspPage = forward.getForward();
                } else {
                    jspPage = command.getName() + ".jsp";
                }
                jspPage = PathConstant.PAGES_LOCATION + jspPage;
                logger.debug(String.format("Request for URI %s is forwarded to JSP %s", requestedUri, jspPage));
                getServletContext().getRequestDispatcher(jspPage).forward(req, resp);
            }
        } catch (PersistentException | CommandException e) {
            logger.error("It is impossible to process request", e);
            ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(req);
            req.setAttribute(AttributeConstant.ERROR.value(), rb.getString("app.global.process.error"));
            getServletContext().getRequestDispatcher(PathConstant.ERROR_PAGES_LOCATION).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().destroy();
    }
}
