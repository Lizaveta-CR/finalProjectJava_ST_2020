package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.getSession(false).invalidate();
        Forward forward = new Forward("/login.jsp");
        return forward;
    }
}
