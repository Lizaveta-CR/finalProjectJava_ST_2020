package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.ServiceFactory;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Abstract command class. Is used to perform different operations according to user request
 */
@Setter
public abstract class Command {
    private String name;
    private Set<Role> allowRoles = new HashSet<>();
    protected ServiceFactory factory;

    public Set<Role> getAllowRoles() {
        return allowRoles;
    }

    public String getName() {
        return name;
    }

    /**
     * Executes given request
     *
     * @param request
     * @param response
     * @return Forward - paged response
     * @throws CommandException
     */
    public abstract Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    /**
     *Is used to build user output
     */
    public static class Forward {
        private String forward;
        private boolean redirect;
        private Map<String, Object> attributes = new HashMap<>();

        public Forward(String forward, boolean redirect) {
            this.forward = forward;
            this.redirect = redirect;
        }

        public Forward(String forward) {
            this(forward, false);
        }

        public String getForward() {
            return forward;
        }

        public void setForward(String forward) {
            this.forward = forward;
        }

        public boolean isRedirect() {
            return redirect;
        }

        public void setRedirect(boolean redirect) {
            this.redirect = redirect;
        }

        public Map<String, Object> getAttributes() {
            return attributes;
        }

    }
}
