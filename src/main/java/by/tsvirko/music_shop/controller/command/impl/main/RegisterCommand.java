package by.tsvirko.music_shop.controller.command.impl.main;

import by.tsvirko.music_shop.controller.command.Command;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.ValidatorFactory;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegisterCommand extends Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) {
        Forward forward = new Forward("/welcome.jsp");
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
            request.setAttribute("message", "Incorrect data. Check fields");
            forward.setForward("/registration.jsp");
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
                //TODO: наверное через forward.attr
                session.setAttribute("authorizedUser", user.getName());
            } catch (ServicePersistentException e) {
                logger.error("User can not created because of service error", e.getMessage());
                forward.setForward("/registration.jsp");
                return forward;
            }
        }
        return forward;
    }
}
