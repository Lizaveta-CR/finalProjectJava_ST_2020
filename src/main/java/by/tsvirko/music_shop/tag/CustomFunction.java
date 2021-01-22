package by.tsvirko.music_shop.tag;

import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;

import java.math.BigDecimal;
import java.util.List;

public class CustomFunction {
    /**
     * Checks if BigDecimal is zero
     *
     * @param value
     * @return true if zero, otherwise - false
     */
    public static boolean isBigDecimalZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Checks if 2 strings are equal
     *
     * @param str1
     * @param str2
     * @return true if str1 equals str2, otherwise - false
     */
    public static boolean equals(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * Checks if list size is greater than zero
     *
     * @param list - list to check
     * @return true if size is greater than zero, otherwise - false
     */
    public static boolean length(List<Component> list) {
        return list.size() > 0;
    }

    /**
     * Checks if user is buyer
     *
     * @param user - user to check role
     * @return true if user is buyer, otherwise - false
     */
    public static boolean isBuyer(User user) {
        return user.getRole().equals(Role.BUYER);
    }

    /**
     * Checks if user is administrator
     *
     * @param user - user to check role
     * @return true if user is administrator, otherwise - false
     */
    public static boolean isAdmin(User user) {
        return user.getRole().equals(Role.ADMINISTRATOR);
    }

    /**
     * Checks if user is manager
     *
     * @param user - user to check role
     * @return true if user is manager, otherwise - false
     */
    public static boolean isManager(User user) {
        return user.getRole().equals(Role.MANAGER);
    }
}
