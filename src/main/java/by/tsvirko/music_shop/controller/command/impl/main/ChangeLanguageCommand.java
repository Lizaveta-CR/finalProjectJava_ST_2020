package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class ChangeLanguageCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String referer = request.getHeader("Referer");
        return new Forward(referer, true);
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
