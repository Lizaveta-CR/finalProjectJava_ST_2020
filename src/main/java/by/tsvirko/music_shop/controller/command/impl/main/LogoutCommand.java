package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command to log out from system
 */
public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.getSession(false).invalidate();
        return new Forward(PathConstnant.LOGOUT, true);
    }
}
