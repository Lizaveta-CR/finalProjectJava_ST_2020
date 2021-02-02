package by.tsvirko.music_shop.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Track the events of creating a request when accessing the servlet and its destruction
 */
public class RequestListener implements ServletRequestListener {
    private static final Logger logger = LogManager.getLogger(RequestListener.class);

    public void requestInitialized(ServletRequestEvent ev) {
        HttpServletRequest req = (HttpServletRequest) ev.getServletRequest();
        String uri = req.getRequestURI();
        String id = req.getRequestedSessionId();
        logger.info("Request Initialized for " + uri + "\n" + "Request Initialized with ID= " + id);
    }

    public void requestDestroyed(ServletRequestEvent ev) {
        logger.info("Request Destroyed: " + ev.getServletRequest().getAttribute("lifecycle"));
    }
}
