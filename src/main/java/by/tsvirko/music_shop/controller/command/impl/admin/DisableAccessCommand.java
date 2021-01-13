package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DisableAccessCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(DisableAccessCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.BUYER_ID.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                BuyerService buyerService = factory.getService(BuyerService.class);
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
        return new Forward(PathConstnant.ADMIN_FORM, true);
    }
}
