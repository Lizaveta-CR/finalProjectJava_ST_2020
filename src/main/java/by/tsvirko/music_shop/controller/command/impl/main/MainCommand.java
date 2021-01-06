package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class MainCommand extends Command {
    @Override
    public Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
//        request.getSession(false)
        return new Forward("/index.jsp", true);
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
