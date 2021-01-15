package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GenerateBuyerCommand extends AdminCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String parameter = request.getParameter(ParameterConstant.AMOUNT.value());
        if (!parameter.isEmpty() && parameter != null) {
            try {
                BuyerService service = factory.getService(BuyerService.class);
                List<Buyer> buyers = service.findAll();
            } catch (ServicePersistentException e) {
                e.printStackTrace();
            }
        }
    }
}
