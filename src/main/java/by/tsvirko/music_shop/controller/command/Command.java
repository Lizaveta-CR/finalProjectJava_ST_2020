package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.ServiceFactory;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Setter
public abstract class Command {
    protected ServiceFactory factory;

    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
