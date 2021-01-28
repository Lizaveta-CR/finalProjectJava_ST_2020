package by.tsvirko.music_shop.service.validator.impl;

import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuyerValidator implements Validator<Buyer> {
    @Override
    public Buyer validate(HttpServletRequest request) throws IncorrectFormDataException {
        Buyer buyer = new Buyer();
        String parameter = request.getParameter(ParameterConstant.EMAIL.value());
        if (parameter != null && !parameter.isEmpty() && isEmailValid(parameter)) {
            buyer.setEmail(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.EMAIL.value(), parameter);
        }
        parameter = request.getParameter(ParameterConstant.TELEPHONE.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                buyer.setTelephone(Long.parseLong(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter);
        }
        parameter = request.getParameter(ParameterConstant.BALANCE.value());
        if (parameter != null && !parameter.isEmpty() && isMoney(parameter)) {
            try {
                if (parameter.contains(",")) {
                    parameter = parameter.replace(",", ".");
                }
                if (Double.valueOf(parameter) > 0) {
                    buyer.setBalance(new BigDecimal(parameter));
                } else {
                    throw new IncorrectFormDataException(ParameterConstant.BALANCE.value(), parameter);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.BALANCE.value(), parameter);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.BALANCE.value(), parameter);
        }
        return buyer;
    }

    @Override
    public void validate(Buyer entity, HttpServletRequest request) throws IncorrectFormDataException {
        Buyer buyer = validate(request);
        String email = buyer.getEmail();
        if (!email.isEmpty() && email != null) {
            entity.setEmail(email);
        }
        Long telephone = buyer.getTelephone();
        if (telephone != null) {
            entity.setTelephone(telephone);
        }
        BigDecimal balance = buyer.getBalance();
        if (balance != null) {
            entity.setBalance(balance);
        }
    }

    private boolean isMoney(String price) {
        String regExp = "[0-9]+([,.][0-9]{1,2})?";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
                , Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegex.matcher(email);
        return matcher.find();
    }
}
