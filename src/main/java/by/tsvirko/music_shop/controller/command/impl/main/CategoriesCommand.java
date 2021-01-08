package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

public class CategoriesCommand extends Command {
    @Override
    public Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            CategoryService service = factory.getService(CategoryService.class);
            Category category = service.getCategory();
            List<Component> components = category.getComponents();
            request.setAttribute(AttributeConstant.CATEGORY.value(), components);
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
        return new Forward("/products/list.jsp");
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
