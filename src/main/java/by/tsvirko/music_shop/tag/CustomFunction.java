package by.tsvirko.music_shop.tag;

import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;

import java.math.BigDecimal;
import java.util.List;

public class CustomFunction {
    /**
     * Checks if BigDecimal is zero
     *
     * @param value
     * @return
     */
    public static boolean isBigDecimalZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean equals(String str1, String str2) {
        return str1.equals(str2);
    }

    public static boolean length(List<Component> list) {
        return list.size() > 0;
    }

    public static boolean isBuyer(User user) {
        return user.getRole().equals(Role.BUYER);
    }

    public static boolean isAdmin(User user) {
        return user.getRole().equals(Role.ADMINISTRATOR);
    }

    public static boolean isManager(User user) {
        return user.getRole().equals(Role.MANAGER);
    }
}
