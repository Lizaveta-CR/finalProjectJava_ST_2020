package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewFeedBackPage extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(ViewFeedBackPage.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.ORDER.value());
        if (parameter != null) {
            try {
                OrderItemService service = factory.getService(OrderItemService.class);
                List<Product> products = service.readProductsByOrderId(Integer.parseInt(parameter));
                request.setAttribute(ParameterConstant.PRODUCTS.value(), products);
                logger.info(String.format("Products for order with id=%s were read.", parameter));
            } catch (ServicePersistentException e) {
                logger.error(String.format("Products for order with id=%s can not be read. " +
                        "Service Exception occurred", parameter));
                return new Forward("buyer/buyerForm", true);
            }
        }
        return null;
    }
}