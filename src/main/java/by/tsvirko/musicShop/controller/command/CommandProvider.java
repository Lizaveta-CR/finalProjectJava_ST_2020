package by.tsvirko.musicShop.controller.command;

import by.tsvirko.musicShop.controller.command.exception.CommandException;
import by.tsvirko.musicShop.controller.command.impl.AllProductsCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider() {
        repository.put(CommandName.ALL_PRODUCTS, new AllProductsCommand());
    }

    public Command getCommand(String name) throws CommandException {
        CommandName commandName;
        Command command;
        commandName = CommandName.valueOf(name.toUpperCase());
        command = repository.get(commandName);
        if (command == null) {
            throw new CommandException();
        }
        return command;
    }
}
