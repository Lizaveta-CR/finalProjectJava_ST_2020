package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class BuyerFormCommand extends BuyerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int page = 1;
        int recordsPerPage = 5;
        String parameter = request.getParameter(ParameterConstant.PAGE.value());
        if (parameter != null) {
            page = Integer.parseInt(parameter);
        }
        try {
            OrderService service = factory.getService(OrderService.class);
            Map<Integer, List<Order>> map = service.find((page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute(AttributeConstant.ORDERS.value(), map.get(noOfRecords));
            request.setAttribute(AttributeConstant.NO_OF_PAGES.value(), noOfPages);
            request.setAttribute(AttributeConstant.CURRENT_PAGE.value(), page);
        } catch (ServicePersistentException e) {
            //todo
            e.printStackTrace();
        }

        return null;
    }
}
