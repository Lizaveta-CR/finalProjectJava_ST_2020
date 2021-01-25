package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Command for viewing edit form
 */
public class BuyerViewEditFormCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerViewEditFormCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            User authorizedUser = (User) request.getSession(false).getAttribute(AttributeConstant.AUTHORIZED_USER.value());
            BuyerService buyerService = factory.getService(BuyerService.class);
            Buyer buyer = buyerService.findById(authorizedUser.getId());
            request.setAttribute(AttributeConstant.BUYER_INFO.value(), buyer);
        } catch (ServicePersistentException e) {
            logger.info("Buyer can not be found in system");
        }
        return null;
    }
}
