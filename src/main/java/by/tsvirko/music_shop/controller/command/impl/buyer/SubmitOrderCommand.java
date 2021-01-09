package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.impl.main.LoginCommand;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
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
import java.util.ResourceBundle;

public class SubmitOrderCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(SubmitOrderCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("/products/list", true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        Order order = (Order) request.getSession(false).getAttribute(AttributeConstant.ORDER.value());

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
        }
//        if (user != null && buyer != null) {
//            try {
//                UserService userService = factory.getService(UserService.class);
//                BuyerService buyerService = factory.getService(BuyerService.class);
//                userService.save(user);
//                buyer.setId(user.getId());
//                buyerService.save(buyer);
//                HttpSession session = request.getSession();
//                session.setAttribute(AttributeConstant.AUTHORIZED_USER.value(), user);
//                session.setAttribute(AttributeConstant.AUTHORIZED_BUYER.value(), buyer);
//                session.setAttribute(AttributeConstant.MENU.value(), menu.get(user.getRole()));
//            } catch (ServicePersistentException e) {
//                logger.error("User can not created because of service error", e.getMessage());
//                request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.register.duplicate"));
//                return null;
//            }
//        }
        return forward;
    }
}
