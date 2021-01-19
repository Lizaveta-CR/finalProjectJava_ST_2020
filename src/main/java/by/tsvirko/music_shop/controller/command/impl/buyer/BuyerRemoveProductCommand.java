package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.TotalPriceUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
/**
 * Command for removing product from order
 */
public class BuyerRemoveProductCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerRemoveProductCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("/buyer/order.html", true);
        String parameter = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (!parameter.isEmpty()) {
            try {
                ProductService productService = factory.getService(ProductService.class);
                Product product = productService.findById(Integer.parseInt(parameter));
                HttpSession session = request.getSession(false);
                Order order = (Order) session.getAttribute(AttributeConstant.ORDER.value());
                Map<Product, Byte> map = (Map<Product, Byte>) session.getAttribute(AttributeConstant.ORDER_ITEM.value());
                if (order != null && product != null) {
                    order.removeProduct(product);
                    map.remove(product);
                    order.setPrice(TotalPriceUtil.countPrice(map));
                    logger.info("Product with id=" + product.getId() + " was removed from order");
                }
                return forward;
            } catch (ServicePersistentException e) {
                logger.error("Service exception occurred while removing product in BuyerRemoveProductCommand class");
            }
        }
        return forward;
    }
}
