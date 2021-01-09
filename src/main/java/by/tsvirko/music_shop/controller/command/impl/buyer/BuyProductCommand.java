package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BuyProductCommand extends BuyerCommand {
//    private Order order = new Order();

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Forward forward = new Forward("/buyer/order", true);
        String parameter = request.getParameter(ParameterConstant.PRODUCT_ID.value());
        if (!parameter.isEmpty()) {
            try {
                ProductService productService = factory.getService(ProductService.class);
                Product product = productService.findById(Integer.parseInt(parameter));
//                order.addProduct(product);
                HttpSession session = request.getSession(false);
                Order order = (Order) session.getAttribute(AttributeConstant.ORDER.value());
                if (order == null) {
                    order = new Order();
                    session.setAttribute(AttributeConstant.ORDER.value(), order);
                }
                order.addProduct(product);
//                forward.getAttributes().put(AttributeConstant.MESSAGE.value(), product);
//                request.setAttribute("order", order);
                return forward;
            } catch (ServicePersistentException e) {
                //TODO
                e.printStackTrace();
            }
        }
        forward.setForward("/products/list");
        return forward;
    }
}
