package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddPersonalCommand extends ManagerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return null;
    }
}
