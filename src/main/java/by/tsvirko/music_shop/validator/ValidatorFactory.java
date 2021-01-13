package by.tsvirko.music_shop.validator;

import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import by.tsvirko.music_shop.validator.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ValidatorFactory {
    private static Map<Class<? extends Entity>, Validator> validators = new ConcurrentHashMap<>();

    static {
        validators.put(User.class, new UserValidator());
        validators.put(Buyer.class, new BuyerValidator());
        validators.put(Address.class, new AddressValidator());
        validators.put(Order.class, new OrderValidator());
        validators.put(Product.class, new ProductValidator());
    }

    public static <Type extends Entity> Validator<Type> getValidator(Class<Type> key) throws ValidatorException {
        Validator value = validators.get(key);
        if (value != null) {
            return (Validator<Type>) value;
        }
        throw new ValidatorException("Validator can not be created");
    }
}
