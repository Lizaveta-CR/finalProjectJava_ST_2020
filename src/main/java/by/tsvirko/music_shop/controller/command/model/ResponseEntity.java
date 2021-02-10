package by.tsvirko.music_shop.controller.command.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Is used to build users' response.
 */
public class ResponseEntity {
    /**
     * Forward.
     */
    private String forward;
    /**
     * If forward should be redirected.
     */
    private boolean redirect;
    /**
     * Map of forward attributes.
     */
    private Map<String, Object> attributes = new HashMap<>();

    /**
     * Forward constructor.
     *
     * @param forward  - given forward
     * @param redirect - {@code true} if response should be redirected, otherwise - {@code false}
     */
    public ResponseEntity(String forward, boolean redirect) {
        this.forward = forward;
        this.redirect = redirect;
    }

    /**
     * Forward constructor.
     * <p>Initializes forward with {@code false} redirect</>
     *
     * @param forward - given forward
     */
    public ResponseEntity(String forward) {
        this(forward, false);
    }

    /**
     * Returns forward.
     *
     * @return forward
     */
    public String getForward() {
        return forward;
    }

    /**
     * Sets forward.
     *
     * @param forward - given forward to set
     */
    public void setForward(String forward) {
        this.forward = forward;
    }

    /**
     * Checks for redirect.
     *
     * @return if response should be redirected
     */
    public boolean isRedirect() {
        return redirect;
    }

    /**
     * Sets redirect(true/false).
     *
     * @param redirect if redirect is {@code true} or {@code false}
     */
    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    /**
     * Returns forward attributes.
     *
     * @return Map<String, Object> - map of attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
