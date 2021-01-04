package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewRegisterCommand extends Command {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return new Forward("/registration.jsp");
    }
}
