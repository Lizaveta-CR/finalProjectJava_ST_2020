package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * View 'about' page.
 */
public class AboutViewCommand extends Command {
    private static final String SUFFIX = ".jsp";

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String forwardName = getName().concat(SUFFIX);
        return new Forward(forwardName);
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
