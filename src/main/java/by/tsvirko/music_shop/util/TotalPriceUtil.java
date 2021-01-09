package by.tsvirko.music_shop.util;

import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;

import java.math.BigDecimal;
import java.util.Map;

public class TotalPriceUtil {
    private static final BigDecimal KOEFFICIENT = new BigDecimal(0.03);

    public static BigDecimal countPrice(Map<Product, Byte> map) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Byte> entry : map.entrySet()) {
            BigDecimal productPrice = entry.getKey().getPrice();
            total = total.add(productPrice.multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }

    public static BigDecimal countPrice(Map.Entry<Product, Byte> entry) {
        return entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue()));
    }

    public static BigDecimal countPrice(Order order, BigDecimal bonus) {
        return order.getPrice().subtract(bonus);
    }

    public static BigDecimal countBonus(Order order) {
        return order.getPrice().multiply(KOEFFICIENT);
    }
}
