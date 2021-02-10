package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

/**
 * Command for viewing submit form
 */
public class BuyerViewSubmitOrderCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerViewSubmitOrderCommand.class);
    private static final String SUFFIX = ".jsp";

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        Buyer buyer = (Buyer) request.getSession(false).getAttribute(AttributeConstant.AUTHORIZED_BUYER.value());
        if (buyer != null) {
            if (buyer.getAddress() == null) {
                try {
                    AddressService addressService = factory.getService(ServiceType.ADDRESS);
                    Address address = addressService.findById(buyer.getId());
                    if (address != null) {
                        buyer.setAddress(address);
                    }
                } catch (ServicePersistentException e) {
                    ResponseEntity responseEntity = new ResponseEntity(PathConstant.BUYER_ADDRESS, true);
                    responseEntity.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.address.empty"));
                    logger.info(String.format("Buyer %s was redirected to fill address", buyer.getId()));
                    return responseEntity;
                }
            }
            Order order = (Order) request.getSession(false).getAttribute(AttributeConstant.ORDER.value());
            order.setBuyer(buyer);
        } else {
            ResponseEntity responseEntity = new ResponseEntity(PathConstant.ERROR_PAGES_LOCATION);
            responseEntity.getAttributes().put(AttributeConstant.ERROR.value(), "app.mess.authorize");
            return responseEntity;
        }
        String forwardName = getName().concat(SUFFIX);
        return new ResponseEntity(forwardName);
    }
}
