package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.ValidatorFactory;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;

public class EditAddressCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(EditAddressCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        Buyer authorizedBuyer = (Buyer) request.getSession(false).getAttribute("authorizedBuyer");
        try {
            BuyerService buyerService = factory.getService(BuyerService.class);
            AddressService addressService = factory.getService(AddressService.class);

            Buyer buyer = buyerService.findById(authorizedBuyer.getId());
            Validator<Address> validator = ValidatorFactory.getValidator(Address.class);

            Address address = buyer.getAddress();
            if (address != null) {
                validator.validate(address, request);
                addressService.update(address);
            } else {
                Address newAddress = validator.validate(request);
                newAddress.setId(buyer.getId());
                addressService.save(newAddress);
            }
        } catch (ValidatorException e) {
            logger.error("Address can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.warn("Incorrect data was found when updating address", e);
            Forward forward = new Forward("/buyer/address", true);
            forward.setForward("/buyer/address");
            forward.getAttributes().put("message", rb.getString("app.message.user.edit.incorrect"));
            return forward;
        } catch (ServicePersistentException e) {
            logger.error("Service can not be instantiated");
            logger.warn(String.format("Incorrect data was found when buyer tried to change address"));
        }
        return new Forward("/buyer/buyerForm", true);
    }
}
