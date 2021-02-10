package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.constant.PathConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BuyerProductsByRateCommand extends BuyerCommand {
    private static final Logger logger = LogManager.getLogger(BuyerProductsByRateCommand.class);
    private static final int MAX_MARK_VALUE = 10;
    private static final int MIN_MARK_VALUE = 0;

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ResponseEntity responseEntity = new ResponseEntity(PathConstant.PRODUCTS_LIST, true);
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        int mark = 0;
        String parameter = request.getParameter(ParameterConstant.MARK.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                mark = Integer.parseInt(parameter);
                if (mark < MIN_MARK_VALUE && mark > MAX_MARK_VALUE) {
                    responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.mark.incorrect"));
                    return responseEntity;
                }
            } catch (IllegalArgumentException e) {
                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.mark.incorrect"));
                return responseEntity;
            }
        }
        //to see all products
        if (mark == 0) {
            return responseEntity;
        }
        List<Product> products;
        try {
            ProductService productService = factory.getService(ServiceType.PRODUCT);
            products = productService.readProductsByMark(mark);
        } catch (ServicePersistentException e) {
            logger.warn("Products by mark can not be read");
            responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.mark.no_products"));
            return responseEntity;
        }
        Map<Integer, Integer> productsRate = new HashMap<>();
        Category category = null;
        if (!products.isEmpty()) {
            try {
                CategoryService categoryService = factory.getService(ServiceType.CATEGORY);
                category = categoryService.getSimpleCategory();
                categoryService.buildListProductCategories(category, products);
            } catch (ServicePersistentException e) {
                logger.warn("Products can not be combined with categories:" + e.getMessage());
                responseEntity.getAttributes().put(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.mark.category.error"));
                return responseEntity;
            }
        }
        if (category != null) {
            request.setAttribute(AttributeConstant.CATEGORY.value(), category);
            for (Product product : products) {
                productsRate.put(product.getId(), mark);
            }
            request.setAttribute(AttributeConstant.RATEMAP.value(), productsRate);
            request.setAttribute(AttributeConstant.REDIRECTED_DATA.value(), rb.getString("app.message.mark.see_all"));
        }
        responseEntity.setForward(PathConstant.PRODUCTS_LIST_JSP);
        responseEntity.setRedirect(false);
        return responseEntity;
    }
}
