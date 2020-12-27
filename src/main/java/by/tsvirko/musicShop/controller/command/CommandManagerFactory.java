package by.tsvirko.musicShop.controller.command;

import by.tsvirko.musicShop.service.ServiceFactory;

public class CommandManagerFactory {
    public static CommandManager getManager(ServiceFactory factory) {
        return new CommandProvider(factory);
    }
}
