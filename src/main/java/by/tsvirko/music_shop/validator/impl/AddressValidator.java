package by.tsvirko.music_shop.validator.impl;

import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class AddressValidator implements Validator<Address> {
    @Override
    public Address validate(HttpServletRequest request) throws IncorrectFormDataException {
        Address address = new Address();
        String parameter = request.getParameter("country");
        if (parameter != null && !parameter.isEmpty()) {
            Country country = new Country();
            country.setName(parameter);
            address.setCountry(country);
        } else {
            throw new IncorrectFormDataException("country", parameter);
        }
        parameter = request.getParameter("city");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setCity(parameter);
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("city", parameter);
            }
        } else {
            throw new IncorrectFormDataException("city", parameter);
        }
        parameter = request.getParameter("zip_code");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setZipCode(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("zip_code", parameter);
            }
        } else {
            throw new IncorrectFormDataException("zip_code", parameter);
        }
        parameter = request.getParameter("street");
        if (parameter != null && !parameter.isEmpty()) {
            address.setStreet(parameter);
        } else {
            throw new IncorrectFormDataException("street", parameter);
        }
        parameter = request.getParameter("apartmentNumber");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setApartmentNumber(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("apartmentNumber", parameter);
            }
        } else {
            throw new IncorrectFormDataException("apartmentNumber", parameter);
        }
        parameter = request.getParameter("houseNumber");
        if (parameter != null && !parameter.isEmpty()) {
            try {
                address.setHouseNumber(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("houseNumber", parameter);
            }
        } else {
            throw new IncorrectFormDataException("houseNumber", parameter);
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
