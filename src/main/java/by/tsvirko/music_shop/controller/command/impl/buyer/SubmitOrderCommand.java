package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.OrderItemUtil;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import by.tsvirko.music_shop.util.TotalPriceUtil;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.ValidatorFactory;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SubmitOrderCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(SubmitOrderCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("/products/list", true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        HttpSession session = request.getSession(false);
        Order order = (Order) session.getAttribute(AttributeConstant.ORDER.value());

        if (order != null) {
            try {
                Validator<Order> orderValidator = ValidatorFactory.getValidator(Order.class);

                orderValidator.validate(order, request);
            } catch (ValidatorException e) {
                logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
            } catch (IncorrectFormDataException e) {
                logger.warn("Incorrect data was found when saving order", e);
                forward.setForward("/buyer/order/submit");
                forward.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.order.incorrect"));
                return forward;
            }
            String bonus = request.getParameter(ParameterConstant.BONUS.value());
            if (bonus != null) {
                order.setPrice(TotalPriceUtil.countPrice(order, new BigDecimal(bonus)));
                order.getBuyer().setBonus(BigDecimal.ZERO);
            }
            Map<Product, Byte> map = (Map<Product, Byte>) session.getAttribute(AttributeConstant.ORDER_ITEM.value());
            if (map != null) {
                try {
                    OrderService orderService = factory.getService(OrderService.class);
                    orderService.save(order);

                    List<OrderItem> orderItems = OrderItemUtil.buildOrderItems(map);
                    orderItems.forEach(orderItem -> orderItem.setId(order.getId()));
                    OrderItemService orderItemService = factory.getService(OrderItemService.class);
                    orderItemService.save(orderItems);
                } catch (ServicePersistentException e) {
                    logger.error("Service error occurred");
                    forward.setForward(getName());
                    forward.getAttributes().put(AttributeConstant.MESSAGE.value(),
                            rb.getString("app.message.order.noMoney"));
                    return forward;
                }
                session.removeAttribute(AttributeConstant.ORDER_ITEM.value());
            }
            session.removeAttribute(AttributeConstant.ORDER.value());
        }
        return forward;
    }
}