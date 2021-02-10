package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for viewing personal
 */
public class ViewPersonalCommand extends ManagerCommand {
    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserService userService = factory.getService(ServiceType.USER);
            List<User> personal = userService.findEmployees();
            User currentUser = (User) request.getSession().getAttribute(AttributeConstant.AUTHORIZED_USER.value());

            //for not seeing himself
            if (currentUser != null) {
                personal.remove(currentUser);
            }
            request.setAttribute(AttributeConstant.PERSONAL.value(), personal);
        } catch (ServicePersistentException e) {
            return new ResponseEntity(PathConstant.MAIN_JSP, true);
        }
        return new ResponseEntity(PathConstant.MANAGER_PERSONAL_JSP);
    }
}
