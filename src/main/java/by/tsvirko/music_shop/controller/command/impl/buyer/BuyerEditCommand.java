package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.ValidatorFactory;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

/**
 * Command for editing buyer information
 */
public class BuyerEditCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerEditCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward(PathConstnant.BUYER_FORM, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        HttpSession session = request.getSession(false);

        User authorizedUser = null;
        Buyer authorizedBuyer = null;
        if (session != null) {
            authorizedUser = (User) session.getAttribute(AttributeConstant.AUTHORIZED_USER.value());
            authorizedBuyer = (Buyer) session.getAttribute(AttributeConstant.AUTHORIZED_BUYER.value());
        } else {
            forward.setForward(PathConstnant.ERROR_PAGES_LOCATION);
            forward.getAttributes().put(AttributeConstant.ERROR.value(), "app.mess.authorize");
            return forward;
        }
        try {
            BuyerService buyerService = factory.getService(BuyerService.class);
            UserService userService = factory.getService(UserService.class);

            Validator<Buyer> buyerValidator = ValidatorFactory.getValidator(Buyer.class);
            buyerValidator.validate(authorizedBuyer, request);

            buyerService.update(authorizedBuyer);

            String login = request.getParameter(ParameterConstant.LOGIN.value());
            if (login != null && !login.isEmpty()) {
                authorizedUser.setLogin(login);
                userService.save(authorizedUser);
            }
        } catch (ValidatorException e) {
            logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when updating user", e);
            forward.setForward(PathConstnant.BUYER_EDIT);
            forward.getAttributes().put("message", rb.getString("app.message.user.edit.incorrect"));
            return forward;
        } catch (ServicePersistentException e) {
            logger.error("Service can not be instantiated");
            logger.warn(String.format("Incorrect data was found when user \"%s\" tried to change information", authorizedUser.getLogin()));
            forward.setForward(PathConstnant.BUYER_EDIT);
            forward.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.user.dublicate"));
        }
        return forward;
    }
}
