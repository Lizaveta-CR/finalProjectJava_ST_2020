package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.service.CountryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditAddressCommand extends BuyerCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            CountryService service = factory.getService(CountryService.class);
            List<String> countries = service.readNames();

        } catch (ServicePersistentException e) {
            e.printStackTrace();
        }
        return new Forward("/buyer/address");
    }
}
