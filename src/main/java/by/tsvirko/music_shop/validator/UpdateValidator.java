package by.tsvirko.music_shop.validator;

import by.tsvirko.music_shop.domain.Entity;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface UpdateValidator<Type extends Entity> {
    void validate(Type type, HttpServletRequest request) throws IncorrectFormDataException;
}
