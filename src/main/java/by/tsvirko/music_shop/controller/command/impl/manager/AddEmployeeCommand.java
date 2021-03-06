package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
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
import java.util.ResourceBundle;

/**
 * Command to add employee
 */
public class AddEmployeeCommand extends ManagerCommand {
    private static final Logger logger = LogManager.getLogger(AddEmployeeCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.MANAGER_PERSONAL, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        User employee = null;
        try {
            Validator<User> validator = ValidatorFactory.getValidator(ValidatorType.USER);
            employee = validator.validate(request);
        } catch (ValidatorException e) {
            logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when saving user", e);
            responseEntity.setForward(PathConstant.MANAGER_ADD_PERSONAL);
            responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.register.incorrect"));
            return responseEntity;
        }
        if (employee != null) {
            try {
                UserService service = factory.getService(ServiceType.USER);
                service.save(employee);
            } catch (ServicePersistentException e) {
                logger.error("User can not created because of service error", e.getMessage());
                responseEntity.setForward(PathConstant.MANAGER_ADD_PERSONAL);
                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.register.duplicate"));
                return responseEntity;
            }
        }
        return responseEntity;
    }
}
