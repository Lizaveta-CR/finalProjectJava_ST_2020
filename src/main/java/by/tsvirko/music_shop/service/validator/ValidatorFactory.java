package by.tsvirko.music_shop.service.validator;

import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.validator.exceprion.ValidatorException;
import by.tsvirko.music_shop.service.validator.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ValidatorFactory {
    private static Map<ValidatorType, Validator> validators = new ConcurrentHashMap<>();

    static {
        validators.put(ValidatorType.USER, new UserValidator());
        validators.put(ValidatorType.BUYER, new BuyerValidator());
        validators.put(ValidatorType.ADDRESS, new AddressValidator());
        validators.put(ValidatorType.ORDER, new OrderValidator());
        validators.put(ValidatorType.PRODUCT, new ProductValidator());
    }

    public static <Type extends Entity> Validator<Type> getValidator(ValidatorType key) throws ValidatorException {
        Validator value = validators.get(key);
        if (value != null) {
            return (Validator<Type>) value;
        }
        throw new ValidatorException("Validator can not be created");
    }
}
