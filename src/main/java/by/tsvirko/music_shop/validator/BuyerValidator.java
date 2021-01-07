package by.tsvirko.music_shop.validator;

import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerValidator implements Validator<Buyer> {
    @Override
    public Buyer validate(HttpServletRequest request) throws IncorrectFormDataException {
        Buyer buyer = new Buyer();
        String parameter = request.getParameter("email");
        if (parameter != null && !parameter.isEmpty() && isEmailValid(parameter)) {
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
        if (parameter != null && !parameter.isEmpty() && isMoney(parameter)) {
            try {
                if (parameter.contains(",")) {
                    parameter = parameter.replace(",", ".");
                }
                if (Double.valueOf(parameter) > 0) {
                    buyer.setBalance(new BigDecimal(parameter));
                } else {
                    throw new IncorrectFormDataException("balance", parameter);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("balance", parameter);
            }
        } else {
            throw new IncorrectFormDataException("balance", parameter);
        }
        return buyer;
    }

    private boolean isMoney(String price) {
        String regExp = "[0-9]+([,.][0-9]{1,2})?";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegex.matcher(email);
        return matcher.find();
    }
}
