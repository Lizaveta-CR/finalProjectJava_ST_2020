package by.tsvirko.music_shop.controller.command.impl.manager;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.controller.command.impl.admin.AdminEditProductsCommand;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.FileUtil;
import by.tsvirko.music_shop.util.ResourceBundleUtil;
import by.tsvirko.music_shop.util.exception.FileUtilException;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.ValidatorFactory;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ResourceBundle;

public class AddProductCommand extends ManagerCommand {
    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward(PathConstnant.PRODUCTS_CREATE, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);

        Product product = null;
        try {
            Validator<Product> productValidator = ValidatorFactory.getValidator(Product.class);
            product = productValidator.validate(request);
        } catch (ValidatorException e) {
            logger.error("Product can not validated because of ValidatorFactory error", e.getMessage());
        } catch (IncorrectFormDataException e) {
            logger.info("Incorrect data while updating product", e.getMessage());
            forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.incorrect"));
            return forward;
        }
        String description;
        try {
            Part filePart = request.getPart("file");
            description = FileUtil.readFile(filePart);
        } catch (IOException | ServletException | FileUtilException e) {
            logger.error("File can not be processed", e.getMessage());
            forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.incorrect.descr"));
            return forward;
        }
        if (product != null && description != null) {
            product.setDescription(description);
            Producer producer = product.getProducer();
            try {
                if (producer.getId() == null) {
                    ProducerService producerService = factory.getService(ProducerService.class);
                    producerService.save(producer);
                }
            } catch (ServicePersistentException e) {
                logger.warn("Service for saving producer can not be instantiated: ", e.getMessage());
                forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.producer.dublicate"));
                return forward;
            }
            try {
                ProductService productService = factory.getService(ProductService.class);
                productService.save(product);
            } catch (ServicePersistentException e) {
                logger.warn("Service for saving product can not be instantiated: ", e.getMessage());
                forward.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.product.dublicate"));
                return forward;
            }
        }
        forward.setForward(PathConstnant.PRODUCTS_LIST);
        return forward;
    }
}