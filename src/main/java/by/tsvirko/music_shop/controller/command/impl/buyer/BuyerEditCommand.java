package by.tsvirko.music_shop.controller.command.impl.buyer;

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
import java.util.ResourceBundle;

public class BuyerEditCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerEditCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("/buyer/buyerForm", true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        User authorizedUser = (User) request.getSession(false).getAttribute("authorizedUser");
        Buyer authorizedBuyer = (Buyer) request.getSession(false).getAttribute("authorizedBuyer");
        try {
            BuyerService buyerService = factory.getService(BuyerService.class);
            UserService userService = factory.getService(UserService.class);
            String password = request.getParameter("password");
            if (!password.isEmpty() && password != null) {
                try {
                    userService.findByLoginAndPassword(authorizedUser.getLogin(), password);
                } catch (ServicePersistentException ex) {
                    logger.info(String.format("User \"%s\" tried to change password and specified the incorrect previous one", authorizedUser.getLogin()));
                    forward.setForward("/buyer/edit");
                    forward.getAttributes().put("message", rb.getString("app.message.user.edit.pass"));
                    return forward;
                }
            }
            Validator<User> userValidator = ValidatorFactory.getValidator(User.class);
            Validator<Buyer> buyerValidator = ValidatorFactory.getValidator(Buyer.class);

            buyerValidator.validate(authorizedBuyer, request);
            userValidator.validate(authorizedUser, request);

            buyerService.save(authorizedBuyer);
            userService.save(authorizedUser);
        } catch (ValidatorException e) {
            logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when updating user", e);
            forward.setForward("/buyer/edit");
            forward.getAttributes().put("message", rb.getString("app.message.user.edit.incorrect"));
            return forward;
        } catch (ServicePersistentException e) {
            logger.error("Service can not be instantiated");
            logger.warn(String.format("Incorrect data was found when user \"%s\" tried to change password", authorizedUser.getLogin()));
        }
        return forward;
    }
}
