package by.tsvirko.music_shop.controller.command.impl.common;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.Menu;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.ValidatorFactory;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.service.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Command to register
 */
public class RegisterCommand extends Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);
    private static Map<Role, List<Menu>> menu = new ConcurrentHashMap<>();

    static {
        menu.put(Role.BUYER, Arrays.asList(new Menu(PathConstnant.BUYER_FORM, "app.menu.myPage")));
    }

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = new Forward(PathConstnant.WELCOME, true);
        ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(request);

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
            logger.info("Incorrect data was found when saving user");
            forward.setForward(PathConstnant.REGISTRATION);
            forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.register.incorrect" + " :" + e.getMessage()));
            return forward;
        }
        if (user != null && buyer != null) {
            try {
                UserService userService = factory.getService(UserService.class);
                BuyerService buyerService = factory.getService(BuyerService.class);
                userService.save(user);
                buyer.setId(user.getId());
                buyerService.save(buyer);
                HttpSession session = request.getSession();
                session.setAttribute(AttributeConstant.AUTHORIZED_USER.value(), user);
                session.setAttribute(AttributeConstant.AUTHORIZED_BUYER.value(), buyer);
                session.setAttribute(AttributeConstant.MENU.value(), menu.get(user.getRole()));
            } catch (ServicePersistentException e) {
                logger.error("User can not created because of service error", e.getMessage());
                forward.setForward(PathConstnant.REGISTRATION);
                forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.register.duplicate"));
                return forward;
            }
        }
        return forward;
    }

    @Override
    public Set<Role> getAllowRoles() {
        return null;
    }
}
