package by.tsvirko.music_shop.service.validator.exceprion;

import by.tsvirko.music_shop.service.util.ResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;

public class IncorrectFormDataException extends Exception {
    public IncorrectFormDataException(String param, String value, HttpServletRequest request) {
        super(new ResourceBundleUtil().getResourceBundle(request).getString("app.exception.incorrect") + " " + param + ":" + value);
    }
}
