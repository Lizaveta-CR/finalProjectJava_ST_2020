package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProducerService;
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
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Command to add product
 */
public class AddProductCommand extends ManagerCommand {
    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.PRODUCTS_CREATE, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        Product product = null;
        try {
            Validator<Product> productValidator = ValidatorFactory.getValidator(ValidatorType.PRODUCT);
            product = productValidator.validate(request);
        } catch (ValidatorException e) {
            logger.error("Product can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.info("Incorrect data while updating product", e.getMessage());
            responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.incorrect") + " " + e.getMessage());
            return responseEntity;
        }
        String description;
        try {
            Part filePart = request.getPart(ParameterConstant.FILE.value());
            description = new FileHelper().readFile(filePart);
        } catch (IOException | ServletException | FileUtilException e) {
            logger.error("File can not be processed", e.getMessage());
            responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.incorrect.descr"));
            return responseEntity;
        }
        if (product != null && description != null) {
            product.setDescription(description);
            Producer producer = product.getProducer();
            try {
                if (producer.getId() == null) {
                    ProducerService producerService = factory.getService(ServiceType.PRODUCER);
                    producerService.save(producer);
                }
            } catch (ServicePersistentException e) {
                logger.warn("Service for saving producer can not be instantiated: ", e.getMessage());
                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.producer.dublicate"));
                return responseEntity;
            }
            try {
                ProductService productService = factory.getService(ServiceType.PRODUCT);
                productService.save(product);
            } catch (ServicePersistentException e) {
                logger.warn("Service for saving product can not be instantiated: ", e.getMessage());
                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.dublicate"));
                return responseEntity;
            }
        }
        responseEntity.setForward(PathConstant.PRODUCTS_LIST);
        return responseEntity;
    }
}
