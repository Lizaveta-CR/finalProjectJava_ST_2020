package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CategoriesCommand extends Command {
    @Override
    public Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            CategoryService service = factory.getService(CategoryService.class);
            Category category = service.getCategory();
            List<Component> components = category.getComponents();
            request.setAttribute("category", components);
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
        return new Forward("/products/list.jsp");
    }
}
