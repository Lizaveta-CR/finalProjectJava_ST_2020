package by.tsvirko.musicShop.controller.command;

import by.tsvirko.musicShop.controller.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandManager {
    String execute(String action, HttpServletRequest request, HttpServletResponse response) throws CommandException;

    void close();
}
