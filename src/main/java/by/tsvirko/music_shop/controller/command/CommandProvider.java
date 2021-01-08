package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CommandProvider implements CommandManager {
    private ServiceFactory factory;

    public CommandProvider(ServiceFactory factory) {
        this.factory = factory;
    }


    @Override
    public Command.Forward execute(Command command, HttpServletRequest request, HttpServletResponse response) throws CommandException {
        command.setFactory(factory);
        return command.execute(request, response);
    }

    @Override
    public void close() {
        factory.close();
    }
}
