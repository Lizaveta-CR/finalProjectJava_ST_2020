package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.controller.command.model.ResponseEntity;
import by.tsvirko.music_shop.controller.command.constant.AttributeConstant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Command for showing winner within buyers. Only admin access.
 */
public class ShowWinnerCommand extends AdminCommand {
    private static final String SUFFIX = ".jsp";

    @Override
    public ResponseEntity execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession(false);
        Buyer attribute = (Buyer) session.getAttribute(AttributeConstant.BUYER.value());
        if (attribute != null) {
            request.setAttribute(AttributeConstant.BUYER.value(), attribute);
            session.removeAttribute(AttributeConstant.BUYER.value());
        }
        String forwardName = getName().concat(SUFFIX);
        return new ResponseEntity(forwardName);
    }
}
