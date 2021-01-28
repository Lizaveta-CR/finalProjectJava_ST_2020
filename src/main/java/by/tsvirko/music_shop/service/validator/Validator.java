package by.tsvirko.music_shop.service.validator;

import by.tsvirko.music_shop.domain.Entity;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public interface Validator<Type extends Entity> {
    Type validate(HttpServletRequest request) throws IncorrectFormDataException;

    void validate(Type entity, HttpServletRequest request) throws IncorrectFormDataException;
}
