package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminEditProductsViewCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(AdminEditProductsViewCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);

        String parameter = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (parameter != null && !parameter.isEmpty()) {
            session.removeAttribute(AttributeConstant.PRODUCT.value());
            try {
                ProductService service = factory.getService(ProductService.class);
                Product product = service.findById(Integer.parseInt(parameter));
                session.setAttribute(AttributeConstant.PRODUCT.value(), product);
                return null;
            } catch (ServicePersistentException e) {
                logger.warn("Product with id=" + parameter + " can not be read");
            }
        }
        return null;
    }
}