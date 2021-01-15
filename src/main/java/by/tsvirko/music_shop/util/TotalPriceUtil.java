package by.tsvirko.music_shop.util;

import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Total price counter util
 */
public class TotalPriceUtil {
    /**
     * Koefficient to provide bonus percent of total price
     */
    private static final BigDecimal KOEFFICIENT = new BigDecimal(3);

    /**
     * Counts price for products depending on amount
     *
     * @param map, where Product-given product and Byte - amount
     * @return BigDecimal-total price
     */
    public static BigDecimal countPrice(Map<Product, Byte> map) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Byte> entry : map.entrySet()) {
            BigDecimal productPrice = entry.getKey().getPrice();
            total = total.add(productPrice.multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }

    /**
     * Counts price for products depending on amount
     *
     * @param entry, where Product-given product and Byte - amount
     * @return BigDecimal-total price
     */
    public static BigDecimal countPrice(Map.Entry<Product, Byte> entry) {
        return entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue()));
    }

    /**
     * Counts total price for order depending on  bonus
     * @param order
     * @param bonus
     * @return
     */
    public static BigDecimal countPrice(Order order, BigDecimal bonus) {
        return order.getPrice().subtract(bonus);
    }

    /**
     * Counts total bonus depending on KOEFFICIENT
     * @param order
     * @return BigDecimal- total bonus
     */
    public static BigDecimal countBonus(Order order) {
        BigDecimal total = order.getPrice().multiply(KOEFFICIENT).divide(new BigDecimal(100));
        total = total.setScale(2, BigDecimal.ROUND_HALF_UP);
        return total;
    }
}
