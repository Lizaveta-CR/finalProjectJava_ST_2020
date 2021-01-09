package by.tsvirko.music_shop.validator.impl;

import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class OrderValidator implements Validator<Order> {
    @Override
    public void validate(Order entity, HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter("telephone");
        if (parameter != null && !parameter.isEmpty()) {
            long telephone;
            try {
                telephone = Long.parseLong(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("telephone", parameter);
            }
            if (entity.getBuyer().getTelephone().longValue() != telephone) {
                throw new IncorrectFormDataException("telephone", parameter);
            }
        } else {
            throw new IncorrectFormDataException("telephone", parameter);
        }
        parameter = request.getParameter("city");
        if (parameter != null && !parameter.isEmpty()) {
            if (!entity.getBuyer().getAddress().getCity().equals(parameter)) {
                throw new IncorrectFormDataException("city", parameter);
            }
        } else {
            throw new IncorrectFormDataException("city", parameter);
        }
        parameter = request.getParameter("zip_code");
        if (parameter != null && !parameter.isEmpty()) {
            int zip;
            try {
                zip = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("zip_code", parameter);
            }
            if (entity.getBuyer().getAddress().getZipCode() != zip) {
                throw new IncorrectFormDataException("zip_code", parameter);
            }
        } else {
            throw new IncorrectFormDataException("zip_code", parameter);
        }
        parameter = request.getParameter("street");
        if (parameter != null && !parameter.isEmpty()) {
            if (!entity.getBuyer().getAddress().getStreet().equals(parameter)) {
                throw new IncorrectFormDataException("street", parameter);
            }
        } else {
            throw new IncorrectFormDataException("street", parameter);
        }
        parameter = request.getParameter("apartmentNumber");
        if (parameter != null && !parameter.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("apartmentNumber", parameter);
            }
            if (entity.getBuyer().getAddress().getApartmentNumber() != num) {
                throw new IncorrectFormDataException("apartmentNumber", parameter);
            }
        } else {
            throw new IncorrectFormDataException("apartmentNumber", parameter);
        }
        parameter = request.getParameter("houseNumber");
        if (parameter != null && !parameter.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("houseNumber", parameter);
            }
            if (entity.getBuyer().getAddress().getHouseNumber() != num) {
                throw new IncorrectFormDataException("houseNumber", parameter);
            }
        } else {
            throw new IncorrectFormDataException("houseNumber", parameter);
        }
    }

    @Override
    public Order validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
