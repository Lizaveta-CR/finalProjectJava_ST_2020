package by.tsvirko.music_shop.controller;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.CommandManager;
import by.tsvirko.music_shop.controller.command.CommandManagerFactory;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.dao.pool.ConnectionPool;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final String DATASOURCE_NAME = "database";
    private static final String COMMAND_PARAMETER = "command";
    private static final String JSP_LOCATION = "/WEB-INF/jsp/pages";
    //TODO: может это в WEB_INF?
    private static final String JSP_ERROR_LOCATION = "/WEB-INF/jsp/error/error.jsp";

    @Override
    public void init() {
        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
        String driver = resource.getString("db.driver");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));

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
        Command command = (Command) req.getAttribute(COMMAND_PARAMETER);
        try {
            HttpSession session = req.getSession(false);
            if (session != null) {
//                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>) session.getAttribute("redirectedData");
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        req.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute("redirectedData");
                }
            }
            CommandManager actionManager = CommandManagerFactory.getManager(getFactory());
            Command.Forward forward = actionManager.execute(command, req, resp);
            actionManager.close();
            if (session != null && forward != null && !forward.getAttributes().isEmpty()) {
                session.setAttribute("redirectedData", forward.getAttributes());
            }
            String requestedUri = req.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String contextPath = req.getContextPath();
                String redirectedUri = contextPath + forward.getForward();
                logger.debug(String.format("Request for URI \"%s\" id redirected to URI \"%s\"", requestedUri, redirectedUri));
//                getServletContext().getRequestDispatcher(redirectedUri).forward(req, resp);
                resp.sendRedirect(redirectedUri);
            } else {
                String jspPage = null;
                if (forward != null) {
                    jspPage = forward.getForward();
                } else {
                    //TODO: для всех
                    jspPage = command.getName() + ".jsp";
                }
                jspPage = JSP_LOCATION + jspPage;
                logger.debug(String.format("Request for URI \"%s\" is forwarded to JSP \"%s\"", requestedUri, jspPage));
                getServletContext().getRequestDispatcher(jspPage).forward(req, resp);
            }
        } catch (PersistentException | CommandException e) {
            logger.error("It is impossible to process request", e);
//            req.setAttribute("error", "Ошибка обработки данных");
//            getServletContext().getRequestDispatcher(JSP_ERROR_LOCATION).forward(req, resp);
        }
    }
}
