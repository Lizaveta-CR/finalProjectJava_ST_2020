package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.factory.ServiceFactoryImpl;

public class CommandManagerFactory {
    public static CommandManager getManager() throws CommandException {
        try {
            return new CommandProvider(new ServiceFactoryImpl());
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
    }
}
