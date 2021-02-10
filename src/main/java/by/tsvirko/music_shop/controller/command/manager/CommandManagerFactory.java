package by.tsvirko.music_shop.controller.command.manager;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.factory.ServiceFactoryImpl;

/**
 * Command manager factory.
 */
public class CommandManagerFactory {
    /**
     * Creates new {@code CommandManager} with new {@code ServiceFactoryImpl}
     *
     * @return new {@code CommandManager} object
     * @throws CommandException if command can not be obtained or executed
     */
    public static CommandManager getManager() throws CommandException {
        try {
            return new CommandManagerImpl(new ServiceFactoryImpl());
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
    }
}
