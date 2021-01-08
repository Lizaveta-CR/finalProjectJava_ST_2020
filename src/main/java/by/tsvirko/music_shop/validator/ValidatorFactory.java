package by.tsvirko.music_shop.validator;

import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Entity;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.validator.exceprion.ValidatorException;
import by.tsvirko.music_shop.validator.impl.BuyerUpdateValidator;
import by.tsvirko.music_shop.validator.impl.BuyerValidator;
import by.tsvirko.music_shop.validator.impl.UserUpdateValidator;
import by.tsvirko.music_shop.validator.impl.UserValidator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ValidatorFactory {
    private static Map<Class<? extends Entity>, Validator> validators = new ConcurrentHashMap<>();
    private static Map<Class<? extends Entity>, UpdateValidator> updateValidators = new ConcurrentHashMap<>();

    static {
        validators.put(User.class, new UserValidator());
        validators.put(Buyer.class, new BuyerValidator());

        updateValidators.put(Buyer.class, new BuyerUpdateValidator());
        updateValidators.put(User.class, new UserUpdateValidator());
    }

    public static <Type extends Entity> Validator<Type> getValidator(Class<Type> key) throws ValidatorException {
        Validator value = validators.get(key);
        if (value != null) {
            return (Validator<Type>) value;
        }
        throw new ValidatorException("Validator can not be created");
    }

    public static <Type extends Entity> UpdateValidator<Type> getUpdateValidator(Class<Type> key) throws ValidatorException {
        UpdateValidator value = updateValidators.get(key);
        if (value != null) {
            return (UpdateValidator<Type>) value;
        }
        throw new ValidatorException("Validator can not be created");
    }
}
