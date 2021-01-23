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
 * Command for editing buyers' password
 */
public class BuyerEditPassCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerEditPassCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward(PathConstnant.BUYER_FORM, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authorizedUser = (User) session.getAttribute(AttributeConstant.AUTHORIZED_USER.value());
            String password = request.getParameter(ParameterConstant.PASS.value());
            if (!password.isEmpty() && password != null) {
                try {
                    UserService userService = factory.getService(UserService.class);
                    Validator<User> userValidator = ValidatorFactory.getValidator(User.class);
                    userValidator.validate(authorizedUser, request);
                    User foundUser = userService.findByLoginAndPassword(authorizedUser.getLogin(), password);
                    if (foundUser != null) {
                        userService.updatePassword(authorizedUser);
                    } else {
                        forward.setForward(PathConstnant.BUYER_EDIT_PASS);
                        forward.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.user.edit.pass"));
                        return forward;
                    }
                } catch (ServicePersistentException | IncorrectFormDataException ex) {
                    logger.info(String.format("User \"%s\" tried to change password and specified the incorrect previous one", authorizedUser.getLogin()));
                    forward.setForward(PathConstnant.BUYER_EDIT_PASS);
                    forward.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.user.edit.pass"));
                    return forward;
                } catch (ValidatorException e) {
                    logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
                }
            }
        } else {
            forward.setForward(PathConstnant.ERROR_PAGES_LOCATION);
            forward.getAttributes().put(AttributeConstant.ERROR.value(), "app.mess.authorize");
            return forward;
        }
        return forward;
    }
}
