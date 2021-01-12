package by.tsvirko.music_shop.tag;

import java.math.BigDecimal;

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
}
