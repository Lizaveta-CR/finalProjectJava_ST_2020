package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Command for viewing earnings
 */
public class ViewEarningsCommand extends ManagerCommand {
    private static final Logger logger = LogManager.getLogger(ViewEarningsCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.MAIN_JSP);
        try {
            OrderService service = factory.getService(ServiceType.ORDER);
            List<Order> orders = service.findAll();
            request.setAttribute(AttributeConstant.ORDERS.value(), orders);
        } catch (ServicePersistentException e) {
            logger.error("Earning can not be send to manager: " + e.getMessage());
            return responseEntity;
        }
        responseEntity.setForward(PathConstant.MANAG_EARNINGS_JSP);
        return responseEntity;
    }
}
