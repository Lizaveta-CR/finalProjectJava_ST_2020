package by.tsvirko.music_shop.service.validator.impl;

import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

/**
 * Order validator.
 */
public class OrderValidator implements Validator<Order> {
    /**
     * Validates request parameters and returns corresponding {@link Order} entity.
     *
     * @param request - HttpServletRequest
     * @return corresponding to {@param request} {@link Order} entity
     * @throws IncorrectFormDataException if request parameters were incorrect
     */
    @Override
    public void validate(Order entity, HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter(ParameterConstant.TELEPHONE.value());
        if (parameter != null && !parameter.isEmpty()) {
            long telephone;
            try {
                telephone = Long.parseLong(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter, request);
            }
            if (entity.getBuyer().getTelephone().longValue() != telephone) {
                throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.TELEPHONE.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.CITY.value());
        if (parameter != null && !parameter.isEmpty()) {
            if (!entity.getBuyer().getAddress().getCity().equals(parameter)) {
                throw new IncorrectFormDataException(ParameterConstant.CITY.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.CITY.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.ZIP_CODE.value());
        if (parameter != null && !parameter.isEmpty()) {
            int zip;
            try {
                zip = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.ZIP_CODE.value(), parameter, request);
            }
            if (entity.getBuyer().getAddress().getZipCode() != zip) {
                throw new IncorrectFormDataException(ParameterConstant.ZIP_CODE.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.ZIP_CODE.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.STREET.value());
        if (parameter != null && !parameter.isEmpty()) {
            if (!entity.getBuyer().getAddress().getStreet().equals(parameter)) {
                throw new IncorrectFormDataException(ParameterConstant.STREET.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.STREET.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.APARTMENT_NUMBER.value());
        if (parameter != null && !parameter.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.APARTMENT_NUMBER.value(), parameter, request);
            }
            if (entity.getBuyer().getAddress().getApartmentNumber() != num) {
                throw new IncorrectFormDataException(ParameterConstant.APARTMENT_NUMBER.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.APARTMENT_NUMBER.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.HOUSE_NUMBER.value());
        if (parameter != null && !parameter.isEmpty()) {
            int num;
            try {
                num = Integer.parseInt(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.HOUSE_NUMBER.value(), parameter, request);
            }
            if (entity.getBuyer().getAddress().getHouseNumber() != num) {
                throw new IncorrectFormDataException(ParameterConstant.HOUSE_NUMBER.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.HOUSE_NUMBER.value(), parameter, request);
        }
    }

    @Override
    public Order validate(HttpServletRequest request) throws IncorrectFormDataException {
        return null;
    }
}
