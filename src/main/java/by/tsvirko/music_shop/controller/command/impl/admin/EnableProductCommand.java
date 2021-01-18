package by.tsvirko.music_shop.controller.command.impl.admin;

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
/**
 * Command for enabling product access. Only admin access
 */
public class EnableProductCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(ShowUnavailableProductsCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String id = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (id != null && !id.isEmpty()) {
            try {
                ProductService productService = factory.getService(ProductService.class);
                Product product = productService.findById(Integer.parseInt(id));
                product.setAvailable(true);
                productService.save(product);
            } catch (ServicePersistentException e) {
                logger.error("Service can not perform operation with updating product availability");
            }
        }
        return new Forward(PathConstnant.PRODUCTS_LIST, true);
    }
}
