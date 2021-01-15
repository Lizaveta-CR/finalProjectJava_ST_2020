package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateBuyerCommand extends AdminCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward(PathConstnant.ADMIN_BUYERS, true);
        String parameter = request.getParameter(ParameterConstant.AMOUNT.value());
        if (!parameter.isEmpty() && parameter != null) {
            try {
                BuyerService service = factory.getService(BuyerService.class);
                Buyer buyer = service.find(Integer.parseInt(parameter));
                request.setAttribute(AttributeConstant.BUYERS.value(), buyer);
                return forward;
            } catch (ServicePersistentException e) {
//               forward.getAttributes().put(AttributeConstant.MESSAGE)
            }
        }
        return null;
    }
}
