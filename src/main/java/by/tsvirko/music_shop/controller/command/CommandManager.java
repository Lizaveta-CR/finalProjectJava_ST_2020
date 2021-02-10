package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.model.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages given command
 */
public interface CommandManager {
    ResponseEntity execute(Command command, HttpServletRequest request, HttpServletResponse response) throws CommandException;

    void close();
}
