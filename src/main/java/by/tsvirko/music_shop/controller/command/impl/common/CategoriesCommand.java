package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * Command for viewing all products
 */
public class CategoriesCommand extends Command {
    private static final String SUFFIX = ".jsp";

    @Override
    public Command.Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            CategoryService service = factory.getService(ServiceType.CATEGORY);
            Category category = service.getCategory();

            ProductRateService rateService = factory.getService(ServiceType.PRODUCT_RATE);
            Map<Integer, Integer> map = rateService.countAverageRate();

            request.setAttribute(AttributeConstant.CATEGORY.value(), category);
            request.setAttribute(AttributeConstant.RATEMAP.value(), map);
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
        String forwardName = getName().concat(SUFFIX);
        return new Forward(forwardName);
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
