package by.tsvirko.music_shop.controller.command.impl.common.authorizedUser;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command to log out from system
 */
public class LogoutCommand extends AuthorizedUserCommand {
    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.getSession(false).invalidate();
        return new ResponseEntity(PathConstant.LOGOUT, true);
    }
}
