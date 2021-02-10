package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Command for changing language
 */
public class ChangeLanguageCommand extends Command {
    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String referer = request.getParameter(ParameterConstant.PAGE.value());

        if (referer != null && !referer.isEmpty()) {
            return new ResponseEntity(referer, true);
        } else {
            return new ResponseEntity(PathConstant.WELCOME, true);
        }
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
