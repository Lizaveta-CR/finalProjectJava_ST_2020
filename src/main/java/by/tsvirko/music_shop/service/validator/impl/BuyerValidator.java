package by.tsvirko.music_shop.service.validator.impl;

import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Buyer validator.
 */
public class BuyerValidator implements Validator<Buyer> {
    /**
     * Validates request parameters and returns corresponding {@link Buyer} entity.
     *
     * @param request - HttpServletRequest
     * @return corresponding to {@param request} {@link Buyer} entity
     * @throws IncorrectFormDataException if request parameters were incorrect
     */
    @Override
    public Buyer validate(HttpServletRequest request) throws IncorrectFormDataException {
        Buyer buyer = new Buyer();
        String parameter = request.getParameter(ParameterConstant.EMAIL.value());
        if (parameter != null && !parameter.isEmpty() && isEmailValid(parameter)) {
            buyer.setEmail(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.EMAIL.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.TELEPHONE.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                buyer.setTelephone(Long.parseLong(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter, request);
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
                    throw new IncorrectFormDataException(ParameterConstant.BALANCE.value(), parameter, request);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.BALANCE.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.BALANCE.value(), parameter, request);
        }
        return buyer;
    }

    /**
     * Is used when {@link Buyer} entity is being updating. Gets request parameters and updates entity.
     *
     * @param entity- {@link Buyer} entity
     * @param request - HttpServletRequest
     * @throws IncorrectFormDataException if request parameters were incorrect
     */
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

    /**
     * Checks if string matches money regex.
     *
     * @param price - value to check
     * @return {@code true} if price matches money regex, otherwise - {@code false}
     */
    private boolean isMoney(String price) {
        String regExp = "[0-9]+([,.][0-9]{1,2})?";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    /**
     * Checks if email mis valid.
     *
     * @param email - value to check
     * @return {@code true} if email matches email regex, otherwise - {@code false}
     */
    private boolean isEmailValid(String email) {
        Pattern validEmailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
                , Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegex.matcher(email);
        return matcher.find();
    }
}
