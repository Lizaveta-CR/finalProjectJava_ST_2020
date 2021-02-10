package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for showing unavailable products. Only admin access.
 */
public class ShowUnavailableProductsCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(ShowUnavailableProductsCommand.class);
    private static final String SUFFIX = ".jsp";

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ProductService service = factory.getService(ServiceType.PRODUCT);
            List<Product> products = service.findNotAvailable();

            request.setAttribute(AttributeConstant.NOT_AVAILABLE_PRODUCTS.value(), products);
        } catch (ServicePersistentException e) {
            logger.error("Service can not perform operation with retrieving products");
        }
        String forwardName = getName().concat(SUFFIX);
        return new ResponseEntity(forwardName);
    }
}
