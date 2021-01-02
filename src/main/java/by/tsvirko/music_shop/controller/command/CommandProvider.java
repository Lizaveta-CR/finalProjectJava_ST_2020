package by.tsvirko.music_shop.controller.command;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CommandProvider implements CommandManager {
    private ServiceFactory factory;
//    private static final Map<CommandName, Command> repository = new HashMap<>();

//    static {
//        repository.put(CommandName.ALL_PRODUCTS, new AllProductsCommand());
//        repository.put(CommandName.CATEGORIES, new CategoriesCommand());
//    }

    public CommandProvider(ServiceFactory factory) {
        this.factory = factory;
    }
//
//
//    public Command getCommand(String name) throws CommandException {
//        CommandName commandName;
//        Command command;
//        commandName = CommandName.valueOf(name.toUpperCase());
//        command = repository.get(commandName);
//        if (command == null) {
//            throw new CommandException();
//        }
//        return command;
//    }

    @Override
    public Command.Forward execute(Command command, HttpServletRequest request, HttpServletResponse response) throws CommandException {
//        Command command = repository.get(CommandName.valueOf(action.toUpperCase()));
//        if (command != null) {
        command.setFactory(factory);
        return command.execute(request, response);
//        }
//        throw new CommandException();
    }

    @Override
    public void close() {
        factory.close();
    }
}
