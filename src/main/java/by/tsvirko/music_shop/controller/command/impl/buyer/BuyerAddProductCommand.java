package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.helper.TotalPriceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Command for adding product to order
 */
public class BuyerAddProductCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerAddProductCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.BUYER_ORDER, true);
        String parameter = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        String parameterAmount = request.getParameter(ParameterConstant.AMOUNT.value());
        if (!parameter.isEmpty()) {
            try {
                ProductService productService = factory.getService(ServiceType.PRODUCT);
                Product product = productService.findById(Integer.parseInt(parameter));
                HttpSession session = request.getSession(false);
                if (session != null) {
                    Order order = (Order) session.getAttribute(AttributeConstant.ORDER.value());
                    Map<Product, Byte> map = (Map<Product, Byte>) session.getAttribute(AttributeConstant.ORDER_ITEM.value());

                    if (map == null) {
                        map = new HashMap<>();
                        session.setAttribute(AttributeConstant.ORDER_ITEM.value(), map);
                    }

                    if (order == null) {
                        order = new Order();
                        session.setAttribute(AttributeConstant.ORDER.value(), order);
                    }

                    byte amount = 1;
                    if (!parameterAmount.isEmpty()) {
                        amount = Byte.parseByte(parameterAmount);
                        if (amount != 0 && amount > 0) {
                            map.put(product, amount);
                        } else {
                            responseEntity.setForward(PathConstant.PRODUCTS_LIST);
                            responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), "app.mess.not-negative");
                            return responseEntity;
                        }
                    }
                    order.setPrice(new TotalPriceHelper().countPrice(map));
                    order.addProduct(product);
                    logger.info("Product with id=" + product.getId() + " was added to order");
                } else {
                    responseEntity.setForward(PathConstant.LOGIN);
                    responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), "app.mess.authorize");
                    return responseEntity;
                }
            } catch (ServicePersistentException e) {
                logger.error("Service exception occurred while adding product in " +
                        "BuyerAddProductCommand class. No product with id=" + parameter);
            }
        }
        return responseEntity;
    }
}
