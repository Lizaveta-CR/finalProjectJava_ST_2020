package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

public class ViewPersonalCommand extends ManagerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserService userService = factory.getService(UserService.class);

        } catch (ServicePersistentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
