package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
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
import java.util.ResourceBundle;

/**
 * Command for editing buyers' password
 */
public class BuyerEditPassCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerEditPassCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.BUYER_FORM, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        HttpSession session = request.getSession();
        User authorizedUser = (User) session.getAttribute(AttributeConstant.AUTHORIZED_USER.value());
        String password = request.getParameter(ParameterConstant.PASS.value());
        if (!password.isEmpty() && password != null) {
            try {
                UserService userService = factory.getService(ServiceType.USER);
                Validator<User> userValidator = ValidatorFactory.getValidator(ValidatorType.USER);
                userValidator.validate(authorizedUser, request);
                User foundUser = userService.findByLoginAndPassword(authorizedUser.getLogin(), password);
                if (foundUser != null) {
                    userService.updatePassword(authorizedUser);
                } else {
                    responseEntity.setForward(PathConstant.BUYER_EDIT_PASS);
                    responseEntity.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.user.edit.pass"));
                    return responseEntity;
                }
            } catch (IncorrectFormDataException ex) {
                responseEntity.setForward(PathConstant.BUYER_EDIT_PASS);
                responseEntity.getAttributes().put(AttributeConstant.MESSAGE.value(), ex.getMessage());
                return responseEntity;
            } catch (ServicePersistentException ex) {
                logger.info(String.format("User %s tried to change password and specified the incorrect previous one", authorizedUser.getLogin()));
                responseEntity.setForward(PathConstant.BUYER_EDIT_PASS);
                responseEntity.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.user.edit.pass"));
                return responseEntity;
            } catch (ValidatorException e) {
                logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
            }
        }
        return responseEntity;
    }
}
