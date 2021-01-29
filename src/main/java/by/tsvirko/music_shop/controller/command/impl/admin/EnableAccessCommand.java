package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Command for enabling access to buyer. Only admin access
 */
public class EnableAccessCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(EnableAccessCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.BUYER_ID.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                BuyerService buyerService = factory.getService(ServiceType.BUYER);
                Buyer buyer = buyerService.findById(Integer.parseInt(parameter));
                if (buyer != null) {
                    buyer.setEnabled(true);
                    buyerService.update(buyer);
                    logger.info("Buyer with id=" + parameter + " was enabled");
                }
            } catch (ServicePersistentException e) {
                logger.warn("Buyer with id=" + parameter + " can not be enabled");
            }
        }
        return new Forward(PathConstnant.ADMIN_BUYERS, true);
    }
}
