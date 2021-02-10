package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for viewing editing products page. Only admin access.
 */
public class AdminEditProductsViewCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(AdminEditProductsViewCommand.class);
    private static final String SUFFIX = ".jsp";

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String forwardName = getName().concat(SUFFIX);
        ResponseEntity responseEntity = new ResponseEntity(forwardName);
        HttpSession session = request.getSession(false);

        String parameter = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (parameter != null && !parameter.isEmpty()) {
            session.removeAttribute(AttributeConstant.PRODUCT.value());
            try {
                ProductService service = factory.getService(ServiceType.PRODUCT);
                Product product = service.findById(Integer.parseInt(parameter));
                session.setAttribute(AttributeConstant.PRODUCT.value(), product);
                return responseEntity;
            } catch (ServicePersistentException e) {
                logger.warn("Product with id=" + parameter + " can not be read");
            }
        }
        return responseEntity;
    }
}
