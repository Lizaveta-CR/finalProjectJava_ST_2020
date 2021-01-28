package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.ValidatorFactory;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.service.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

/**
 * Command for editing address
 */
public class EditAddressCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(EditAddressCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(request);
        HttpSession session = request.getSession();
        Buyer authorizedBuyer = (Buyer) session.getAttribute(AttributeConstant.AUTHORIZED_BUYER.value());
        Integer buyerId = authorizedBuyer.getId();
        try {
            BuyerService buyerService = factory.getService(BuyerService.class);
            AddressService addressService = factory.getService(AddressService.class);

            Buyer buyerById = buyerService.findById(buyerId);
            Validator<Address> validator = ValidatorFactory.getValidator(Address.class);

            Address address = null;
            try {
                address = addressService.findById(buyerId);
            } catch (ServicePersistentException e) {
                logger.info("Buyer " + buyerById + " did not registered address");
            }

            if (address != null) {
                validator.validate(address, request);
                addressService.update(address);
                logger.info("Address with id=" + buyerById + " was updated");
            } else {
                address = validator.validate(request);
                address.setId(buyerById.getId());
                addressService.save(address);
                logger.info("Address with id=" + buyerById + " was saved");
            }
            authorizedBuyer.setAddress(address);
        } catch (ValidatorException e) {
            logger.error("Address can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when updating address", e);
            Forward forward = new Forward(PathConstnant.BUYER_ADDRESS, true);
            forward.getAttributes().put(AttributeConstant.MESSAGE.value(), rb.getString("app.message.user.edit.incorrect"));
            return forward;
        } catch (ServicePersistentException e) {
            logger.error("Service can not be instantiated");
            logger.warn(String.format("Incorrect data was found when buyer tried to change address"));
        }
        return new Forward(PathConstnant.BUYER_FORM, true);
    }
}
