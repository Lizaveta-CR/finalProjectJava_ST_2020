package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Command for viewing personal
 */
public class ViewAddPersonalCommand extends ManagerCommand {
    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new ResponseEntity(PathConstant.MANAGER_ADD_PERSONAL_JSP);
    }
}
