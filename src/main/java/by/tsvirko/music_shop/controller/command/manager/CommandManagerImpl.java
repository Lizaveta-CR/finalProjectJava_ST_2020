package by.tsvirko.music_shop.controller.command.manager;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Persists Service factory and executes requests with given command
 */
public class CommandManagerImpl implements CommandManager {
    private ServiceFactory factory;

    /**
     * Creates new {@code CommandManagerImpl}. Sets service factory.
     *
     * @param factory factory to access service layer
     */
    public CommandManagerImpl(ServiceFactory factory) {
        this.factory = factory;
    }

    /**
     * Sets service factory to command and processes execution.
     *
     * @param command  command to execute
     * @param request  http request
     * @param response http response
     * @return forward to result page
     */
    @Override
    public ResponseEntity execute(Command command, HttpServletRequest request, HttpServletResponse response) throws CommandException {
        command.setFactory(factory);
        return command.execute(request, response);
    }

    /**
     * Closes service factory
     */
    @Override
    public void close() {
        factory.close();
    }
}
