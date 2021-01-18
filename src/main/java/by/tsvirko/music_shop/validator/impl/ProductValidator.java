package by.tsvirko.music_shop.validator.impl;

import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.validator.Validator;
import by.tsvirko.music_shop.validator.exceprion.IncorrectFormDataException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductValidator implements Validator<Product> {
    /**
     * Validates request, is used for new entities
     *
     * @param request
     * @return Product instance
     * @throws IncorrectFormDataException
     */
    @Override
    public Product validate(HttpServletRequest request) throws IncorrectFormDataException {
        Product product = new Product();
        String parameter = request.getParameter("model");
        if (parameter != null && !parameter.isEmpty()) {
            product.setModel(parameter);
        } else {
            throw new IncorrectFormDataException("model", parameter);
        }
        parameter = request.getParameter("category");
        if (parameter != null && !parameter.isEmpty()) {
            Category category = new Category();
            try {
                category.setId(Integer.parseInt(parameter));
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("category", parameter);
            }
            product.setCategory(category);
        } else {
            throw new IncorrectFormDataException("category", parameter);
        }
        parameter = request.getParameter("producer");
        Producer producer = null;
        if (parameter != null && !parameter.isEmpty()) {
            producer = new Producer();
            String name = request.getParameter("name");
            String countryParam = request.getParameter("country");
            if (name != null && !name.isEmpty() && countryParam != null && !countryParam.isEmpty()) {
                producer.setName(name);
                Country country = new Country();
                country.setName(countryParam);
                producer.setCountry(country);
            } else {
                try {
                    producer.setId(Integer.parseInt(parameter));
                } catch (NumberFormatException e) {
                    throw new IncorrectFormDataException("category", parameter);
                }
            }
            product.setProducer(producer);
        }
        product.setImageUrl("");
        validate(product, request);
        return product;
    }

    private boolean isMoney(String price) {
        String regExp = "[0-9]+([,.][0-9]{1,2})?";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }

    /**
     * Validates request, is used for entities update
     *
     * @param request
     * @throws IncorrectFormDataException
     */
    @Override
    public void validate(Product product, HttpServletRequest request) throws IncorrectFormDataException {
        String parameter = request.getParameter("access");
        if (parameter != null && !parameter.isEmpty()) {
            product.setAvailable(true);
        } else {
            product.setAvailable(false);
        }
        parameter = request.getParameter("description");
        if (parameter != null && !parameter.isEmpty()) {
            product.setDescription(parameter);
        } else {
            product.setDescription("");
        }
        parameter = request.getParameter("price");
        if (parameter != null && !parameter.isEmpty() && isMoney(parameter)) {
            try {
                if (parameter.contains(",")) {
                    parameter = parameter.replace(",", ".");
                }
                if (Double.valueOf(parameter) > 0) {
                    product.setPrice(new BigDecimal(parameter));
                } else {
                    throw new IncorrectFormDataException("price", parameter);
                }
            } catch (NumberFormatException e) {
                throw new IncorrectFormDataException("price", parameter);
            }
        } else {
            throw new IncorrectFormDataException("price", parameter);
        }
    }
}
