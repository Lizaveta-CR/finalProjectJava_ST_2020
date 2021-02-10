package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
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
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.ADMIN_WINNER, true);
        String parameter = request.getParameter(ParameterConstant.AMOUNT.value());
        if (!parameter.isEmpty() && parameter != null) {
            try {
                BuyerService service = factory.getService(ServiceType.BUYER);
                Buyer buyer = service.find(Integer.parseInt(parameter));
                responseEntity.getAttributes().put(AttributeConstant.BUYER.value(), buyer);
            } catch (ServicePersistentException e) {
                responseEntity.setForward(PathConstant.ADMIN_BUYERS);
                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.noBuyer"));
                return responseEntity;
            }
        }
        return responseEntity;
    }
}
