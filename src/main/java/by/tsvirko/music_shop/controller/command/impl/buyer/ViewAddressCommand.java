package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.CountryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ViewAddressCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerEditCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        User authorizedUser = (User) request.getSession(false).getAttribute(AttributeConstant.AUTHORIZED_USER.value());
        try {
            CountryService service = factory.getService(CountryService.class);
            List<String> countries = service.readNames();
            request.setAttribute(AttributeConstant.COUNTRIES.value(), countries);

            AddressService addressService = factory.getService(AddressService.class);
            Address address = addressService.findById(authorizedUser.getId());
            if (address != null) {
                request.setAttribute(AttributeConstant.ADDRESS.value(), address);
            }
        } catch (ServicePersistentException e) {
            logger.info("Service error occurred");
        }
        return null;
    }
}