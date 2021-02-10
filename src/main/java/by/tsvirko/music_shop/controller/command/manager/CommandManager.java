package by.tsvirko.music_shop.controller.command.manager;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.model.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manages given command
 */
public interface CommandManager {
    /**
     * Executes command.
     *
     * @param command  command to execute
     * @param request  http request
     * @param response http response
     * @return forward to result page
     */
    ResponseEntity execute(Command command, HttpServletRequest request, HttpServletResponse response) throws CommandException;

    /**
     * Shuts down manager and closes all resources.
     */
    void close();
}
