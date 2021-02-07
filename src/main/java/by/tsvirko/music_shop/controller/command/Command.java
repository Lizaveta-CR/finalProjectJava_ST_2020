package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.factory.ServiceFactory;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Abstract command class. Is used to perform different operations according to user request.
 */
@Setter
public abstract class Command {
    /**
     * Command name
     */
    private String name;
    /**
     * Command allowed roles
     */
    private Set<Role> allowRoles = new HashSet<>();
    /**
     * Service factory
     */
    protected ServiceFactory factory;

    /**
     * Returns command allowed roles
     *
     * @return set of allowed roles to this command
     */
    public Set<Role> getAllowRoles() {
        return allowRoles;
    }

    /**
     * Returns command name
     *
     * @return command name
     */
    public String getName() {
        return name;
    }

    /**
     * Executes given request.
     *
     * @param request  - client request
     * @param response - response
     * @return Forward - paged response
     * @throws CommandException
     */
    public abstract Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    /**
     * Is used to build user output.
     */
    public static class Forward {
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
         * @param redirect - if forward should be redirected(true/false)
         */
        public Forward(String forward, boolean redirect) {
            this.forward = forward;
            this.redirect = redirect;
        }

        /**
         * Forward constructor.
         * <p>Initializes forward with false redirect</>
         *
         * @param forward - given forward
         */
        public Forward(String forward) {
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
         * @return
         */
        public boolean isRedirect() {
            return redirect;
        }

        /**
         * Sets redirect(true/false).
         *
         * @param redirect if redirect is true or false
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

        /**
         * Adds suffix to forward.
         *
         * @param suffix - given suffix to add
         */
        public void addSuffix(String suffix) {
            forward = forward.concat(suffix);
        }
    }
}
