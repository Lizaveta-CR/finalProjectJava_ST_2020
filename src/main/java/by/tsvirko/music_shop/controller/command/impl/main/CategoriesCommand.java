package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Command for viewing all products
 */
public class CategoriesCommand extends Command {
    @Override
    public Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            CategoryService service = factory.getService(CategoryService.class);
            Category category = service.getCategory();

            ProductRateService rateService = factory.getService(ProductRateService.class);
            Map<Integer, Integer> map = rateService.countAverageRate();

            request.setAttribute(AttributeConstant.CATEGORY.value(), category);
            request.setAttribute(AttributeConstant.RATEMAP.value(), map);
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
        return null;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
