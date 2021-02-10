package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for viewing edit form
 */
public class BuyerViewEditFormCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerViewEditFormCommand.class);
    private static final String SUFFIX = ".jsp";

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            User authorizedUser = (User) request.getSession(false).getAttribute(AttributeConstant.AUTHORIZED_USER.value());
            BuyerService buyerService = factory.getService(ServiceType.BUYER);
            Buyer buyer = buyerService.findById(authorizedUser.getId());
            request.setAttribute(AttributeConstant.BUYER_INFO.value(), buyer);
        } catch (ServicePersistentException e) {
            logger.info("Buyer can not be found in system");
        }
        String forwardName = getName().concat(SUFFIX);
        return new ResponseEntity(forwardName);
    }
}
