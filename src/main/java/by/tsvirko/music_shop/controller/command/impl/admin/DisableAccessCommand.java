package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
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
 * Command for disabling access to buyer. Only admin access
 */
public class DisableAccessCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(DisableAccessCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.BUYER_ID.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                BuyerService buyerService = factory.getService(ServiceType.BUYER);
                Buyer buyer = buyerService.findById(Integer.parseInt(parameter));
                if (buyer != null) {
                    buyer.setEnabled(false);
                    buyerService.update(buyer);
                    logger.info("Buyer with id=" + parameter + " was disabled");
                }
            } catch (ServicePersistentException e) {
                logger.warn("Buyer with id=" + parameter + " can not be disabled");
            }
        }
        return new ResponseEntity(PathConstant.ADMIN_BUYERS, true);
    }
}
