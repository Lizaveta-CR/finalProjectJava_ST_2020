package by.tsvirko.music_shop.service.validator.impl;

import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;

public class AddressValidator implements Validator<Address> {
    @Override
    public Address validate(HttpServletRequest request) throws IncorrectFormDataException {
        Address address = new Address();
        String parameter = request.getParameter(ParameterConstant.COUNTRY.value());
        if (parameter != null && !parameter.isEmpty()) {
            Country country = new Country();
            country.setName(parameter);
            address.setCountry(country);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.COUNTRY.value(), parameter,request);
        }
        parameter = request.getParameter(ParameterConstant.CITY.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setCity(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.CITY.value(), parameter,request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.CITY.value(), parameter,request);
        }
        parameter = request.getParameter(ParameterConstant.ZIP_CODE.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setZipCode(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.ZIP_CODE.value(), parameter,request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.ZIP_CODE.value(), parameter,request);
        }
        parameter = request.getParameter(ParameterConstant.STREET.value());
        if (parameter != null && !parameter.isEmpty()) {
            address.setStreet(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.STREET.value(), parameter,request);
        }
        parameter = request.getParameter(ParameterConstant.APARTMENT_NUMBER.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setApartmentNumber(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.APARTMENT_NUMBER.value(), parameter,request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.APARTMENT_NUMBER.value(), parameter,request);
        }
        parameter = request.getParameter(ParameterConstant.HOUSE_NUMBER.value());
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setHouseNumber(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.HOUSE_NUMBER.value(), parameter,request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.HOUSE_NUMBER.value(), parameter,request);
        }
        return address;
    }

    @Override
    public void validate(Address entity, HttpServletRequest request) throws IncorrectFormDataException {
        Address address = validate(request);
        Country country = address.getCountry();
        if (country != null) {
            entity.setCountry(country);
        }
        String city = address.getCity();
        if (!city.isEmpty() && city != null) {
            entity.setCity(city);
        }
        Integer zipCode = address.getZipCode();
        if (zipCode != null) {
            entity.setZipCode(zipCode);
        }
        String street = address.getStreet();
        if (!street.isEmpty() && street != null) {
            entity.setStreet(street);
        }
        Integer apartmentNumber = address.getApartmentNumber();
        if (apartmentNumber != null) {
            entity.setApartmentNumber(apartmentNumber);
        }
        Integer houseNumber = address.getHouseNumber();
        if (houseNumber != null) {
            entity.setHouseNumber(houseNumber);
        }
    }
}
