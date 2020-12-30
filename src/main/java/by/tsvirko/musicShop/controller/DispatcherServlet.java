package by.tsvirko.musicShop.controller;

import by.tsvirko.musicShop.controller.command.CommandManager;
import by.tsvirko.musicShop.controller.command.CommandManagerFactory;
import by.tsvirko.musicShop.controller.command.exception.CommandException;
import by.tsvirko.musicShop.dao.database.TransactionFactoryImpl;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.service.ServiceFactory;
import by.tsvirko.musicShop.service.impl.ServiceFactoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

//@WebServlet(name = "DispatcherServlet", urlPatterns = "/")
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final String DATASOURCE_NAME = "database";
    private static final String COMMAND_PARAMETER = "command";

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
        String page = null;
        try {
            CommandManager manager = CommandManagerFactory.getManager(getFactory());
            String parameter = req.getParameter(COMMAND_PARAMETER);
            if (parameter != null) {
                page = manager.execute(parameter, req, resp);
            } else {
//                page = "/WEB-INF/error/error.jsp";
            }
        } catch (CommandException | PersistentException e) {
            logger.error("It is impossible to process request", e);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        if (dispatcher != null) {
            logger.debug(String.format("Request for URI %s is forwarded to JSP %s", req.getRequestURI(), page));
            dispatcher.forward(req, resp);
        }
//        else {
//            getServletContext().getRequestDispatcher("/WEB-INF/pages/welcome.jsp").forward(req, resp);
//        }
    }
}