package by.tsvirko.music_shop.validator;

import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class BuyerValidator implements Validator<Buyer> {
    @Override
    public Buyer validate(HttpServletRequest request) throws IncorrectFormDataException {
        Buyer buyer = new Buyer();
        //TODO:
        String parameter = request.getParameter("identity");
//        if (parameter != null) {
//            try {
//                buyer.setId(Integer.parseInt(parameter));
//            } catch (NumberFormatException e) {
//                throw new IncorrectFormDataException("identity", parameter);
//            }
//        }
        parameter = request.getParameter("email");
        if (parameter != null && !parameter.isEmpty()) {
            buyer.setEmail(parameter);
        } else {
            throw new IncorrectFormDataException("email", parameter);
        }
        parameter = request.getParameter("telephone");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                buyer.setTelephone(Long.parseLong(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("telephone", parameter);
            }
        } else {
            throw new IncorrectFormDataException("telephone", parameter);
        }
        parameter = request.getParameter("balance");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                buyer.setBalance(new BigDecimal(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("balance", parameter);
            }
        } else {
            throw new IncorrectFormDataException("balance", parameter);
        }
        return buyer;
    }
}
