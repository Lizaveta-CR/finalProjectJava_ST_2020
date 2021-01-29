package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ResourceBundle;
/**
 * Command for generating winner within buyer. Only admin access
 */
public class GenerateBuyerCommand extends AdminCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = new ResourceBundleUtil().getResourceBundle(request);
        Forward forward = new Forward(PathConstnant.ADMIN_WINNER, true);
        String parameter = request.getParameter(ParameterConstant.AMOUNT.value());
        if (!parameter.isEmpty() && parameter != null) {
            try {
                BuyerService service = factory.getService(ServiceType.BUYER);
                Buyer buyer = service.find(Integer.parseInt(parameter));
                forward.getAttributes().put(AttributeConstant.BUYER.value(), buyer);
            } catch (ServicePersistentException e) {
                forward.setForward(PathConstnant.ADMIN_BUYERS);
                forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.noBuyer"));
                return forward;
            }
        }
        return forward;
    }
}
