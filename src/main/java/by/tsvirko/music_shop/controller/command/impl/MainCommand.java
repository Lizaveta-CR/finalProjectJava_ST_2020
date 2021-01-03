package by.tsvirko.music_shop.controller.command.impl;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainCommand extends Command {
    @Override
    public Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
//        if (request.getSession(false).getAttribute("authorizedUser") != null) ;
//        {
//            request.s
        return new Forward("/index.jsp", true);
//        }
    }
}
