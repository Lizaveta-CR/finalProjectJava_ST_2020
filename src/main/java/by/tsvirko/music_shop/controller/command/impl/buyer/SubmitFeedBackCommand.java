package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.ProductRate;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for submitting feedback
 */
public class SubmitFeedBackCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(SubmitFeedBackCommand.class);

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String mark = request.getParameter(ParameterConstant.MARK.value());
        String productId = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (mark != null && productId != null && !mark.isEmpty()
                && !productId.isEmpty()) {
            try {
                ProductService productService = factory.getService(ServiceType.PRODUCT);
                Product product = productService.findById(Integer.parseInt(productId));
                if (product != null) {
                    ProductRateService service = factory.getService(ServiceType.PRODUCT_RATE);
                    Buyer buyer = (Buyer) request.getSession(false).getAttribute(AttributeConstant.AUTHORIZED_BUYER.value());
                    ProductRate productRate = new ProductRate();
                    productRate.setBuyer(buyer);
                    productRate.setMark(Byte.parseByte(mark));
                    productRate.setProduct(product);
                    service.save(productRate);
                }
            } catch (ServicePersistentException e) {
                logger.error(String.format("Unable to save feedback for product with id=%s", productId));
            }
        }
        return new Forward(PathConstnant.BUYER_FORM, true);
    }
}
