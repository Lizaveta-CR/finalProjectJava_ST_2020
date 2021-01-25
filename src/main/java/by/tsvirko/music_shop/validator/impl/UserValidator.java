package by.tsvirko.music_shop.validator.impl;

import by.tsvirko.music_shop.controller.command.constant.ParameterConstant;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class UserValidator implements Validator<User> {

    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        User user = new User();
        String parameter = request.getParameter(ParameterConstant.IDENTITY.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.IDENTITY.value(), parameter);
            }
        }
        parameter = request.getParameter(ParameterConstant.LOGIN.value());
        if (parameter != null && !parameter.isEmpty()) {
            user.setLogin(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.LOGIN.value(), parameter);
        }
        parameter = request.getParameter(ParameterConstant.NAME.value());
        if (parameter != null && !parameter.isEmpty()) {
            user.setName(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.NAME.value(), parameter);
        }
        parameter = request.getParameter(ParameterConstant.SURNAME.value());
        if (parameter != null && !parameter.isEmpty()) {
            user.setSurname(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.SURNAME.value(), parameter);
        }

        String password = request.getParameter(ParameterConstant.PASS.value());
        String confirmedPassword = request.getParameter(ParameterConstant.CONFIRM_PASS.value());
        if (!password.isEmpty() && !confirmedPassword.isEmpty() && password != null && confirmedPassword != null) {
            if (password.equals(confirmedPassword)) {
                user.setPassword(password);
            } else {
                throw new IncorrectFormDataException(ParameterConstant.PASS.value(), password);
            }
        }
        parameter = request.getParameter(ParameterConstant.ROLE.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setRole(Role.getByIdentity(Integer.parseInt(parameter)));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IncorrectFormDataException(ParameterConstant.ROLE.value(), parameter);
            }
        } else {
            user.setRole(Role.BUYER);
        }
        return user;
    }

    @Override
    public void validate(User user, HttpServletRequest request) throws IncorrectFormDataException {
        String newPassword = request.getParameter(ParameterConstant.NEW_PASS.value());
        String newConfirmedPassword = request.getParameter(ParameterConstant.CONFIRM_PASS.value());

        if (!newPassword.isEmpty() && !newConfirmedPassword.isEmpty() && newPassword != null && newConfirmedPassword != null) {
            if (newPassword.equals(newConfirmedPassword)) {
                user.setPassword(newPassword);
            } else {
                throw new IncorrectFormDataException(ParameterConstant.NEW_PASS.value(), newPassword);
            }
        }
    }
}
