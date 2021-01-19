package by.tsvirko.music_shop.controller.command.impl.buyer;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for viewing order
 */
public class ViewOrderCommand extends BuyerCommand {

    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        Order order = (Order) session.getAttribute(AttributeConstant.ORDER.value());
        request.setAttribute(AttributeConstant.ORDER.value(), order);
        return null;
    }
}
