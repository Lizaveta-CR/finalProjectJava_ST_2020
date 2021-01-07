package by.tsvirko.music_shop.validator;

import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class UserValidator implements Validator<User> {
    //TODO: private ResourceBundle rb;

    @Override
    public User validate(HttpServletRequest request) throws IncorrectFormDataException {
        User user = new User();
        String parameter = request.getParameter("identity");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("identity", parameter);
            }
        }
        parameter = request.getParameter("login");
        if (parameter != null && !parameter.isEmpty()) {
            user.setLogin(parameter);
        } else {
            throw new IncorrectFormDataException("login", parameter);
        }
        parameter = request.getParameter("name");
        if (parameter != null && !parameter.isEmpty()) {
            user.setName(parameter);
        } else {
            throw new IncorrectFormDataException("name", parameter);
        }
        parameter = request.getParameter("surname");
        if (parameter != null && !parameter.isEmpty()) {
            user.setSurname(parameter);
        } else {
            throw new IncorrectFormDataException("surname", parameter);
        }

        String password = request.getParameter("password");
        String confirmedPassword = request.getParameter("confirm_password");
        if (!password.isEmpty() && !confirmedPassword.isEmpty()) {
            if (password.equals(confirmedPassword)) {
                user.setPassword(password);
            } else {
                throw new IncorrectFormDataException("password", password);
            }
        }

        parameter = request.getParameter("role");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                user.setRole(Role.getByIdentity(Integer.parseInt(parameter)));
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IncorrectFormDataException("role", parameter);
            }
        } else {
            user.setRole(Role.BUYER);
        }
        return user;
    }
}