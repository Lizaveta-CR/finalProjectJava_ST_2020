package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.factory.ServiceFactory;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
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
     * Executes given request and returns ResponseEntity object .
     *
     * @param request  - client request
     * @param response - response
     * @return Forward - paged response
     * @throws CommandException if command exception occurs
     */
    public abstract ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
