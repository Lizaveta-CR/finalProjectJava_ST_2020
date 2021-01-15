package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowWinnerCommand extends AdminCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        Buyer attribute = (Buyer) session.getAttribute(AttributeConstant.BUYER.value());
        if (attribute != null) {
            request.setAttribute(AttributeConstant.BUYER.value(), attribute);
            session.removeAttribute(AttributeConstant.BUYER.value());
        }
        return null;
    }
}
