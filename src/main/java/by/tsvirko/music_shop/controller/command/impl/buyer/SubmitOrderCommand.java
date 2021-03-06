package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import by.tsvirko.music_shop.service.helper.TotalPriceHelper;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.ValidatorFactory;
import by.tsvirko.music_shop.service.validator.ValidatorType;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.service.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Command for submitting order
 */
public class SubmitOrderCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(SubmitOrderCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.PRODUCTS_LIST, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        HttpSession session = request.getSession(false);
        Order order = (Order) session.getAttribute(AttributeConstant.ORDER.value());

        if (order != null) {
            try {
                Validator<Order> orderValidator = ValidatorFactory.getValidator(ValidatorType.ORDER);

                orderValidator.validate(order, request);
            } catch (ValidatorException e) {
                logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
            } catch (IncorrectFormDataException e) {
                logger.warn("Incorrect data was found when saving order", e);
                responseEntity.setForward(PathConstant.BUYER_ORDER_SUBMIT);
                responseEntity.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.order.incorrect"));
                return responseEntity;
            }
            String bonus = request.getParameter(ParameterConstant.BONUS.value());
            Buyer buyer = order.getBuyer();
            if (bonus != null) {
                order.setPrice(new TotalPriceHelper().countPrice(order, new BigDecimal(bonus)));
                buyer.setBonus(BigDecimal.ZERO);
            }
            Map<Product, Byte> map = (Map<Product, Byte>) session.getAttribute(AttributeConstant.ORDER_ITEM.value());
            if (map != null) {
                try {
                    OrderService orderService = factory.getService(ServiceType.ORDER);
                    orderService.save(order);

                    OrderItemService orderItemService = factory.getService(ServiceType.ORDER_ITEM);
                    List<OrderItem> orderItems = orderItemService.buildOrderItems(map);
                    orderItems.forEach(orderItem -> orderItem.setId(order.getId()));
                    orderItemService.save(orderItems);
                } catch (ServicePersistentException e) {
                    logger.error("Service error occurred");
                    responseEntity.setForward(PathConstant.BUYER_ORDER_SUBMIT);
                    responseEntity.getAttributes().put(AttributeConstant.MESSAGE.value(),
                            rb.getString("app.message.order.noMoney"));
                    if (bonus != null) {
                        buyer.setBonus(new BigDecimal(bonus));
                    }
                    return responseEntity;
                }
                session.removeAttribute(AttributeConstant.ORDER_ITEM.value());
            }
            session.removeAttribute(AttributeConstant.ORDER.value());
        }
        responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.success"));
        return responseEntity;
    }
}
