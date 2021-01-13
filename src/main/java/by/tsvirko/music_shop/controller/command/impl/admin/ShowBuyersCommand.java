package by.tsvirko.music_shop.controller.command.impl.admin;

import by.tsvirko.music_shop.constant.AttributeConstant;
import by.tsvirko.music_shop.constant.PathConstnant;
import by.tsvirko.music_shop.controller.command.exception.CommandException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO: delete?
public class ShowBuyersCommand extends AdminCommand {
    @Override
    public Forward execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return null;
//        try {
//            BuyerService buyerService = factory.getService(BuyerService.class);
//            List<Buyer> buyers = buyerService.findAll();
//
//            request.setAttribute(AttributeConstant.BUYERS.value(), buyers);
//        } catch (ServicePersistentException e) {
//            e.printStackTrace();
//        }
//        return new Forward(PathConstnant.ADMIN_USERS);
    }
}
