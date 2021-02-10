package by.tsvirko.music_shop.service.validator;

import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.validator.exceprion.ValidatorException;
import by.tsvirko.music_shop.service.validator.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Validator factory class.
 */
public class ValidatorFactory {
    /**
     * Returns validator class by {@link ValidatorType}
     *
     * @param key    - validator type
     * @param <Type> - validator entity  type
     * @return - corresponding validator
     * @throws ValidatorException if validator wasn't found
     */
    public static <Type extends Entity> Validator<Type> getValidator(ValidatorType key) throws ValidatorException {
        switch (key) {
            case USER:
                return (Validator<Type>) new UserValidator();
            case BUYER:
                return (Validator<Type>) new BuyerValidator();
            case ADDRESS:
                return (Validator<Type>) new AddressValidator();
            case ORDER:
                return (Validator<Type>) new OrderValidator();
            case PRODUCT:
                return (Validator<Type>) new ProductValidator();
            default:
                throw new ValidatorException("No such validator");
        }
    }
}
