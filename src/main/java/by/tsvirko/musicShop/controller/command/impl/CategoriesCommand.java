package by.tsvirko.musicShop.controller.command.impl;

import by.tsvirko.musicShop.controller.command.Command;
import by.tsvirko.musicShop.controller.command.exception.CommandException;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.domain.Component;
import by.tsvirko.musicShop.service.CategoryService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CategoriesCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            CategoryService service = factory.getService(CategoryService.class);
            Category category = service.getCategory();
            List<Component> components = category.getComponents();
            request.setAttribute("category", components);
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
        return "/WEB-INF/pages/category.jsp";
    }
}
