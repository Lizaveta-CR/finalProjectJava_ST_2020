package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class BuyerViewSubmitOrderCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerViewSubmitOrderCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        Buyer buyer = (Buyer) request.getSession(false).getAttribute(AttributeConstant.AUTHORIZED_BUYER.value());
        if (buyer.getAddress() == null) {
            try {
                AddressService addressService = factory.getService(AddressService.class);
                Address address = addressService.findById(buyer.getId());
                if (address == null) {
                    Forward forward = new Forward("/buyer/address", true);
                    forward.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.address.empty"));
                    logger.info(String.format("Buyer %s was redirected to fill address", buyer.getId()));
                    return forward;
                }
                buyer.setAddress(address);
            } catch (ServicePersistentException e) {
            }
        }
        Order order = (Order) request.getSession(false).getAttribute(AttributeConstant.ORDER.value());
        order.setBuyer(buyer);
        return null;
    }
}
