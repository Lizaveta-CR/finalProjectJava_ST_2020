package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
/**
 * Command for viewing buyer page
 */
public class BuyerFormCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerFormCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int page = 1;
        int recordsPerPage = 5;
        String parameter = request.getParameter(ParameterConstant.PAGE.value());
        if (parameter != null) {
            try {
                page = Integer.parseInt(parameter);
                if (page <= 0) {
                    return null;
                }
            } catch (NumberFormatException e) {
                return null;
            }
        }
        try {
            OrderService service = factory.getService(OrderService.class);
            Buyer buyer = (Buyer) request.getSession().getAttribute(AttributeConstant.AUTHORIZED_BUYER.value());
            Map<Integer, List<Order>> map = service.find((page - 1) * recordsPerPage, recordsPerPage, buyer.getId());
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute(AttributeConstant.ORDERS.value(), map.get(noOfRecords));
            request.setAttribute(AttributeConstant.NO_OF_PAGES.value(), noOfPages);
            request.setAttribute(AttributeConstant.CURRENT_PAGE.value(), page);
        } catch (ServicePersistentException e) {
            logger.error("Service can not perform operation with retrieving limited data");
        }
        return null;
    }
}
