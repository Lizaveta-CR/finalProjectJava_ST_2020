package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.Menu;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RegisterCommand extends Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private static Map<Role, Menu> menu = new ConcurrentHashMap<>();

    static {
//        TODO:add i18n
//        menu.put(Role.BUYER, "/buyer/buyerForm.jsp");
        menu.put(Role.BUYER, new Menu("/buyer/buyerForm"));
//        menu.put(Role.ADMINISTRATOR, "/admin/adminForm.jsp");
//        menu.put(Role.MANAGER, "/manager/managerForm.jsp");
    }

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = new Forward("/welcome", true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        User user = null;
        Buyer buyer = null;
        try {
            Validator<User> userValidator = ValidatorFactory.getValidator(User.class);
            Validator<Buyer> buyerValidator = ValidatorFactory.getValidator(Buyer.class);


            user = userValidator.validate(request);
            buyer = buyerValidator.validate(request);
        } catch (ValidatorException e) {
            logger.error("User can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when saving user", e);
            forward.setForward("/registration");
            request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.register.incorrect"));
            return null;
        }
        if (user != null && buyer != null) {
            try {
                UserService userService = factory.getService(UserService.class);
                BuyerService buyerService = factory.getService(BuyerService.class);
                userService.save(user);
                buyer.setId(user.getId());
                buyerService.save(buyer);
                HttpSession session = request.getSession();
                //TODO: наверное через forward.attr
                session.setAttribute(AttributeConstant.AUTHORIZED_USER.value(), user);
                session.setAttribute(AttributeConstant.AUTHORIZED_BUYER.value(), buyer);
                session.setAttribute(AttributeConstant.MENU.value(), menu.get(user.getRole()));
            } catch (ServicePersistentException e) {
                logger.error("User can not created because of service error", e.getMessage());
                request.setAttribute(AttributeConstant.MESSAGE.value(), rb.getString("app.message.register.duplicate"));
                return null;
            }
        }
        return forward;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
