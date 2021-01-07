package by.tsvirko.music_shop.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class ResourceBundleUtil {
    public static ResourceBundle getResourceBundle(HttpServletRequest req) {
        return null;
    }
}
//public enum ResourceManager {
//    INSTANCE;
//    private ResourceBundle resourceBundle;
//    private final String resourceName = "property.text";
//
//    ResourceManager() {
//        resourceBundle = ResourceBundle.getBundle(resourceName, Locale.getDefault());
//    }
//
//    public void changeResource(Locale locale) {
//        resourceBundle = ResourceBundle.getBundle(resourceName, locale);
//    }
//
//    public String getString(String key) {
//        return resourceBundle.getString(key);
//    }
//}
