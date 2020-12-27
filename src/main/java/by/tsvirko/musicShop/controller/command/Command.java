package by.tsvirko.musicShop.controller.command;

import by.tsvirko.musicShop.controller.command.exception.CommandException;
import by.tsvirko.musicShop.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Command {
    protected ServiceFactory factory;

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
