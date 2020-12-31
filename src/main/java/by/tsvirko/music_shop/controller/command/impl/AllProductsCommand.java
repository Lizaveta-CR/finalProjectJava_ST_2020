package by.tsvirko.music_shop.controller.command.impl;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllProductsCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ProductService service = factory.getService(ProductService.class);
            List<Product> products = service.findAll();
            request.setAttribute("products", products);
        } catch (ServicePersistentException e) {
            throw new CommandException(e);
        }
        return "/WEB-INF/pages/products.jsp";
    }
}
