package by.tsvirko.music_shop.validator.impl;

import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.validator.UpdateValidator;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class UserUpdateValidator implements UpdateValidator<User> {
    @Override
    public void validate(User user, HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter("login");
        if (parameter != null && !parameter.isEmpty()) {
            user.setLogin(parameter);
        } else {
            throw new IncorrectFormDataException("login", parameter);
        }

        String newPassword = request.getParameter("new-password");
        String newConfirmedPassword = request.getParameter("confirm-password");

        if (!newPassword.isEmpty() && !newConfirmedPassword.isEmpty() && newPassword != null && newConfirmedPassword != null) {
            if (newPassword.equals(newConfirmedPassword)) {
                user.setPassword(newPassword);
            } else {
                throw new IncorrectFormDataException("new-password", newPassword);
            }
        } else {
            throw new IncorrectFormDataException("password", newPassword);
        }
    }
}
