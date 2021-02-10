package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for viewing feedback page.
 */
public class ViewFeedBackPage extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(ViewFeedBackPage.class);
    private static final String SUFFIX = ".jsp";

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.ORDER.value());
        if (parameter != null) {
            try {
                OrderItemService service = factory.getService(ServiceType.ORDER_ITEM);
                List<Product> products = service.readProductsByOrderId(Integer.parseInt(parameter));
                request.setAttribute(ParameterConstant.PRODUCTS.value(), products);
                logger.info(String.format("Products for order with id=%s were read.", parameter));
            } catch (ServicePersistentException e) {
                logger.error(String.format("Products for order with id=%s can not be read. " +
                        "Service Exception occurred", parameter));
                return new ResponseEntity(PathConstant.BUYER_FORM, true);
            }
        }
        String forwardName = getName().concat(SUFFIX);
        return new ResponseEntity(forwardName);
    }
}
