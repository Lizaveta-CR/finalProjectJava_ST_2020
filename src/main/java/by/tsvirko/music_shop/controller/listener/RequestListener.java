package by.tsvirko.music_shop.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Request listener.
 * <p>
 * Tracks the events of creating a request when accessing the servlet and its destruction.
 * </p>
 */
public class RequestListener implements ServletRequestListener {
    private static final Logger logger = LogManager.getLogger(RequestListener.class);

    /**
     * Tracks when request was initialized.
     *
     * @param ev - ServletRequestEvent object
     * @see ServletRequestEvent
     */
    public void requestInitialized(ServletRequestEvent ev) {
        HttpServletRequest req = (HttpServletRequest) ev.getServletRequest();
        String uri = req.getRequestURI();
        String id = req.getRequestedSessionId();
        logger.info("Request Initialized for " + uri + "\n" + "Request Initialized with ID= " + id);
    }

    /**
     * Tracks when request was destroyes.
     *
     * @param ev - ServletRequestEvent object
     * @see ServletRequestEvent
     */
    public void requestDestroyed(ServletRequestEvent ev) {
        logger.info("Request Destroyed: " + ev.getServletRequest().getAttribute("lifecycle"));
    }
}
