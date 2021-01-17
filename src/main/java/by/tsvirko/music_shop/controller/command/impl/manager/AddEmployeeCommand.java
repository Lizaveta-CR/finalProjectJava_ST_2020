package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.User;
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

public class AddEmployeeCommand extends ManagerCommand {
    private static final Logger logger = LogManager.getLogger(AddEmployeeCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward(PathConstnant.MANAGER_PERSONAL, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        User employee = null;
        try {
            Validator<User> validator = ValidatorFactory.getValidator(User.class);
            employee = validator.validate(request);
        } catch (ValidatorException e) {
            logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when saving user", e);
            forward.setForward(PathConstnant.MANAGER_ADD_PERSONAL);
            request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.register.incorrect"));
            return null;
        }
        if (employee != null) {
            try {
                UserService service = factory.getService(UserService.class);
                service.save(employee);
            } catch (ServicePersistentException e) {
                logger.error("User can not created because of service error", e.getMessage());
                request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.register.duplicate"));
                return null;
            }
        }
        return forward;
    }
}
