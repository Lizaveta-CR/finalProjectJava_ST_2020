package by.tsvirko.music_shop.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestEvent;

/**
 * Application context attribute listener.
 * <p>
 * Logs the event when attribute is added, removed or replaced in servlet context.
 * </p>
 */
public class AppContextAttributeListener implements ServletContextAttributeListener {
    private static final Logger logger = LogManager.getLogger(AppContextAttributeListener.class);

    /**
     * Tracks when attribute was added.
     *
     * @param servletContextAttributeEvent - ServletContextAttributeEvent object
     * @see ServletContextAttributeEvent
     */
    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        logger.info("ServletContext attribute added::{" + servletContextAttributeEvent.getName() + "," + servletContextAttributeEvent.getValue() + "}");
    }

    /**
     * Tracks when attribute was replaced.
     *
     * @param servletContextAttributeEvent - ServletContextAttributeEvent object
     * @see ServletContextAttributeEvent
     */
    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        logger.info("ServletContext attribute replaced::{" + servletContextAttributeEvent.getName() + "," + servletContextAttributeEvent.getValue() + "}");
    }

    /**
     * Tracks when attribute was removed.
     *
     * @param servletContextAttributeEvent - ServletContextAttributeEvent object
     * @see ServletContextAttributeEvent
     */
    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        logger.info("ServletContext attribute removed::{" + servletContextAttributeEvent.getName() + "," + servletContextAttributeEvent.getValue() + "}");
    }
}
