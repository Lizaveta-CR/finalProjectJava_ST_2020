package by.tsvirko.musicShop.controller.command.impl;

import by.tsvirko.musicShop.controller.command.Command;
import by.tsvirko.musicShop.controller.command.exception.CommandException;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.ProductService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

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
