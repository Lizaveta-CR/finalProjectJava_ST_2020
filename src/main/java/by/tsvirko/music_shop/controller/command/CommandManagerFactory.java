package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.service.ServiceFactory;

public class CommandManagerFactory {
    public static CommandManager getManager(ServiceFactory factory) {
        return new CommandProvider(factory);
    }
}
