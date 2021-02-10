package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.helper.FileHelper;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import by.tsvirko.music_shop.service.util.exception.FileUtilException;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.ValidatorFactory;
import by.tsvirko.music_shop.service.validator.ValidatorType;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.service.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Command for editing products. Only admin access
 */
public class AdminEditProductsCommand extends AdminCommand {
    private static final Logger logger = LogManager.getLogger(AdminEditProductsCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.MAIN_JSP, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        try {
            String parameter = request.getParameter(ParameterConstant.PRODUCT_ID.value());
            if (parameter != null && !parameter.isEmpty()) {
                ProductService service = factory.getService(ServiceType.PRODUCT);
                Product product = service.findById(Integer.parseInt(parameter));
                Validator<Product> validator = ValidatorFactory.getValidator(ValidatorType.PRODUCT);
                validator.validate(product, request);
                String description;
                try {
                    Part filePart = request.getPart(ParameterConstant.FILE.value());
                    description = new FileHelper().readFile(filePart);
                } catch (IOException | ServletException | FileUtilException e) {
                    logger.error("File can not be processed", e.getMessage());
                    responseEntity.setForward(PathConstant.PRODUCTS_EDIT);
                    responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.incorrect.descr"));
                    return responseEntity;
                }
                if (!description.isEmpty() && description != null) {
                    product.setDescription(description);
                }
                service.save(product);
            }
        } catch (ValidatorException e) {
            logger.error("Product can not validated because of ValidatorFactory error", e.getMessage());
        } catch (ServicePersistentException e) {
            logger.warn("Product Service can not be instantiated");
        } catch (IncorrectFormDataException e) {
            logger.info("Incorrect data while updating product", e.getMessage());
            responseEntity.setForward(PathConstant.PRODUCTS_EDIT);
            responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.incorrect"));
            return responseEntity;
        }
        HttpSession session = request.getSession(false);
        Product attribute = (Product) session.getAttribute(AttributeConstant.PRODUCT.value());
        if (attribute != null) {
            session.removeAttribute(AttributeConstant.PRODUCT.value());
        }
        return responseEntity;
    }
}
