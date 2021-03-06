package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
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
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String id = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (id != null && !id.isEmpty()) {
            try {
                ProductService productService = factory.getService(ServiceType.PRODUCT);
                Product product = productService.findById(Integer.parseInt(id));
                product.setAvailable(true);
                productService.save(product);
            } catch (ServicePersistentException e) {
                logger.error("Service can not perform operation with updating product availability");
            }
        }
        return new ResponseEntity(PathConstant.PRODUCTS_LIST, true);
    }
}
