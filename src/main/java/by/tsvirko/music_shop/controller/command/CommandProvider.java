package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Persists Service factory and executes requests with given command
 */
public class CommandProvider implements CommandManager {
    private ServiceFactory factory;

    public CommandProvider(ServiceFactory factory) {
        this.factory = factory;
    }


    @Override
    public ResponseEntity execute(Command command, HttpServletRequest request, HttpServletResponse response) throws CommandException {
        command.setFactory(factory);
        return command.execute(request, response);
    }

    @Override
    public void close() {
        factory.close();
    }
}
