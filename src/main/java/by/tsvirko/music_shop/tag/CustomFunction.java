package by.tsvirko.music_shop.tag;

import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.util.ResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Custom functions for jsp pages.
 */
public class CustomFunction {
    /**
     * Checks if BigDecimal is zero.
     *
     * @param value
     * @return true if zero, otherwise - false
     */
    public static boolean isBigDecimalZero(BigDecimal value) {
        if (value != null) {
            return value.compareTo(BigDecimal.ZERO) == 0;
        }
        return false;
    }

    /**
     * Checks if 2 strings are equal.
     *
     * @param str1
     * @param str2
     * @return true if str1 equals str2, otherwise - false
     */
    public static boolean equals(String str1, String str2) {
        if (str1 != null && str2 != null) {
            return str1.equals(str2);
        }
        return false;
    }

    /**
     * Checks if list size is greater than zero.
     *
     * @param list - list to check
     * @return true if size is greater than zero, otherwise - false
     */
    public static boolean length(List<Component> list) {
        return list.size() > 0;
    }

    /**
     * Checks if user is buyer.
     *
     * @param user - user to check role
     * @return true if user is buyer, otherwise - false
     */
    public static boolean isBuyer(User user) {
        if (user != null) {
            return user.getRole().equals(Role.BUYER);
        }
        return false;
    }

    /**
     * Checks if user is administrator.
     *
     * @param user - user to check role
     * @return true if user is administrator, otherwise - false
     */
    public static boolean isAdmin(User user) {
        if (user != null) {
            return user.getRole().equals(Role.ADMINISTRATOR);
        }
        return false;
    }

    /**
     * Checks if user is manager.
     *
     * @param user - user to check role
     * @return true if user is manager, otherwise - false
     */
    public static boolean isManager(User user) {
        if (user != null) {
            return user.getRole().equals(Role.MANAGER);
        }
        return false;
    }

    /**
     * Finds Role by identity.
     *
     * @param identity - role identity.
     * @return String value of found Role
     */
    public static String findByRoleIdentity(Integer identity, PageContext pageContext) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        ResourceBundle rb = ResourceBundleUtil.getResourceBundle(request);
        Role role = Role.getByIdentity(identity);
        switch (role) {
            case MANAGER:
                return rb.getString("label.manager");
            case ADMINISTRATOR:
                return rb.getString("label.admin");
            case BUYER:
                return rb.getString("label.buyer");
        }
        return null;
    }
}
