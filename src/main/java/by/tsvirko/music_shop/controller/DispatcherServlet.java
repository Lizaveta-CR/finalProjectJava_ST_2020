package by.tsvirko.music_shop.controller;

import by.tsvirko.music_shop.config.ApplicationConfig;
import by.tsvirko.music_shop.config.exception.ApplicationConfigException;
import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.manager.CommandManager;
import by.tsvirko.music_shop.controller.command.manager.CommandManagerFactory;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
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
 * <p>
 * is used to delegate commands from request, link the
 * command with the corresponding class and return response.
 *
 * @author Tsvirko Lizaveta
 * @version 1.0
 */
@MultipartConfig
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);
    private static final String SUFFIX = ".jsp";

    @Override
    public void init() {
        try {
            ApplicationConfig.getInstance().initApplication();
            logger.info("Application was initialized");
        } catch (ApplicationConfigException e) {
            logger.error("It is impossible to initialize application", e);
            destroy();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            CommandManager actionManager = CommandManagerFactory.getManager();
            ResponseEntity responseEntity = actionManager.execute(command, req, resp);
            actionManager.close();
            if (session != null && responseEntity != null && !responseEntity.getAttributes().isEmpty()) {
                session.setAttribute(AttributeConstant.REDIRECTED_DATA.value(), responseEntity.getAttributes());
            }
            processResponse(responseEntity, req, resp);
        } catch (CommandException e) {
            logger.error("It is impossible to process request", e);
            ResourceBundle rb = ResourceBundleUtil.getResourceBundle(req);
            req.setAttribute(AttributeConstant.ERROR.value(),
                    rb.getString("app.global.process.error")
                            + ":" + e.getMessage());
            getServletContext().getRequestDispatcher(PathConstant.ERROR_PAGES_LOCATION).forward(req, resp);
        }
    }

    private void processResponse(ResponseEntity responseEntity, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String requestedUri = req.getRequestURI();
        if (responseEntity != null && responseEntity.isRedirect()) {
            String contextPath = req.getContextPath();
            String redirectedUri = contextPath + responseEntity.getForward();
            logger.debug(String.format("Request for URI %s id redirected to URI %s", requestedUri, redirectedUri));
            resp.sendRedirect(redirectedUri);
        } else {
            String jspPage = null;
            if (responseEntity != null) {
                jspPage = responseEntity.getForward();
            } else {
                Command command = (Command) req.getAttribute(AttributeConstant.COMMAND.value());
                jspPage = command.getName() + SUFFIX;
            }
            jspPage = PathConstant.PAGES_LOCATION + jspPage;
            logger.debug(String.format("Request for URI %s is forwarded to JSP %s", requestedUri, jspPage));
            getServletContext().getRequestDispatcher(jspPage).forward(req, resp);
        }
    }

    @Override
    public void destroy() {
        ApplicationConfig.getInstance().destroyApplication();
    }
}
