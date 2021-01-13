package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.impl.buyer.BuyerEditCommand;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class AdminFormCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(AdminFormCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int page = 1;
        int recordsPerPage = 6;
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
            BuyerService buyerService = factory.getService(BuyerService.class);
            Map<Integer, List<Buyer>> map = buyerService.find((page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = 1;
            for (Integer key : map.keySet()) {
                noOfRecords = key;
            }
            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            request.setAttribute(AttributeConstant.BUYERS.value(), map.get(noOfRecords));
            request.setAttribute(AttributeConstant.NO_OF_PAGES.value(), noOfPages);
            request.setAttribute(AttributeConstant.CURRENT_PAGE.value(), page);
        } catch (ServicePersistentException e) {
            logger.error("Service can not perform operation with retrieving limited data");
        }
        return null;
    }
}
