package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;
/**
 * Command for viewing registration page
 */
public class ViewRegisterCommand extends Command {
    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new ResponseEntity(PathConstant.REGISTRATION_JSP);
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
