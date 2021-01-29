package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Command for viewing personal
 */
public class ViewAddPersonalCommand extends ManagerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new Forward(PathConstant.MANAGER_ADD_PERSONAL_JSP);
    }
}
