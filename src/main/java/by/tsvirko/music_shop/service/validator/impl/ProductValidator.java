package by.tsvirko.music_shop.service.validator.impl;

import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.validator.Validator;
import by.tsvirko.music_shop.service.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Product validator.
 */
public class ProductValidator implements Validator<Product> {
    /**
     * Validates request parameters and returns corresponding {@link Product} entity.
     *
     * @param request - HttpServletRequest
     * @return corresponding to {@param request} {@link Product} entity
     * @throws IncorrectFormDataException if request parameters were incorrect
     */
    @Override
    public Product validate(HttpServletRequest request) throws IncorrectFormDataException {
        Product product = new Product();
        String parameter = request.getParameter(ParameterConstant.MODEL.value());
        if (parameter != null && !parameter.isEmpty()) {
            product.setModel(parameter);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.MODEL.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.CATEGORY.value());
        if (parameter != null && !parameter.isEmpty()) {
            Category category = new Category();
            try {
                category.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.CATEGORY.value(), parameter, request);
            }
            product.setCategory(category);
        } else {
            throw new IncorrectFormDataException(ParameterConstant.CATEGORY.value(), parameter, request);
        }
        parameter = request.getParameter(ParameterConstant.PRODUCER.value());
        Producer producer = null;
        if (parameter != null && !parameter.isEmpty()) {
            producer = new Producer();
            String name = request.getParameter(ParameterConstant.NAME.value());
            String countryParam = request.getParameter(ParameterConstant.COUNTRY.value());
            if (name != null && !name.isEmpty() && countryParam != null && !countryParam.isEmpty()) {
                producer.setName(name);
                Country country = new Country();
                country.setName(countryParam);
                producer.setCountry(country);
            } else {
                try {
                    producer.setId(Integer.parseInt(parameter));
                } catch (NumberFormatException e) {
                    throw new IncorrectFormDataException(ParameterConstant.CATEGORY.value(), parameter, request);
                }
            }
            product.setProducer(producer);
        }
        product.setImageUrl("");
        validate(product, request);
        return product;
    }

    /**
     * Checks if string matches money regex.
     *
     * @param price - value to check
     * @return {@code true} if price matches money regex, otherwise - {@code false}
     */
    private boolean isMoney(String price) {
        String regExp = "[0-9]+([,.][0-9]{1,2})?";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    /**
     * Is used when {@link Product} entity is being updating. Gets request parameters and updates entity.
     *
     * @param product- {@link Product} entity
     * @param request  - HttpServletRequest
     * @throws IncorrectFormDataException if request parameters were incorrect
     */
    @Override
    public void validate(Product product, HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter(ParameterConstant.ACCESS.value());
        if (parameter != null && !parameter.isEmpty()) {
            product.setAvailable(true);
        } else {
            product.setAvailable(false);
        }
        parameter = request.getParameter(ParameterConstant.DESCRIPTION.value());
        if (parameter != null && !parameter.isEmpty()) {
            product.setDescription(parameter);
        } else {
            product.setDescription("");
        }
        parameter = request.getParameter(ParameterConstant.PRICE.value());
        if (parameter != null && !parameter.isEmpty() && isMoney(parameter)) {
            try {
                if (parameter.contains(",")) {
                    parameter = parameter.replace(",", ".");
                }
                if (Double.valueOf(parameter) > 0) {
                    product.setPrice(new BigDecimal(parameter));
                } else {
                    throw new IncorrectFormDataException(ParameterConstant.PRICE.value(), parameter, request);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException(ParameterConstant.PRICE.value(), parameter, request);
            }
        } else {
            throw new IncorrectFormDataException(ParameterConstant.PRICE.value(), parameter, request);
        }
    }
}
