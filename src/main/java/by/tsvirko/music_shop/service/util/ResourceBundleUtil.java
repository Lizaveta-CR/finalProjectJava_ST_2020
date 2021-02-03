package by.tsvirko.music_shop.service.util;

import by.tsvirko.music_shop.controller.command.constant.ResourceBundleAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ResourceBundleUtil {
    /**
     * Gets ResourceBundle depending on HttpServletRequest
     *
     * @param req
     * @return ResourceBundle with locale
     */
    public static ResourceBundle getResourceBundle(HttpServletRequest req) {
        Optional<String> lang = Arrays.stream(req.getCookies())
                .filter(c -> ResourceBundleAttribute.LANGUAGE.value().equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();

        if (lang.isPresent()) {
            String[] params = lang.get().split("_");
            Locale currentLocale = new Locale(params[0], params[1]);
            return ResourceBundle.getBundle(ResourceBundleAttribute.RESOURCE.value(), currentLocale);
        } else {
            return ResourceBundle.getBundle(ResourceBundleAttribute.RESOURCE.value()
                    , Locale.getDefault());
        }
    }
}
