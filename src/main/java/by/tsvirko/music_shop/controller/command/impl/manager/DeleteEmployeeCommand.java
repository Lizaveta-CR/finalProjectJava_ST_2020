package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Command to delete employee
 */
public class DeleteEmployeeCommand extends ManagerCommand {
    private static final Logger logger = LogManager.getLogger(DeleteEmployeeCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.EMPLOYEE_ID.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                UserService service = factory.getService(UserService.class);
                service.delete(Integer.parseInt(parameter));
            } catch (ServicePersistentException e) {
                logger.warn("User with id=" + parameter + " can not be deleted");
            }
        }
        return new Forward(PathConstnant.MANAGER_PERSONAL, true);
    }
}
