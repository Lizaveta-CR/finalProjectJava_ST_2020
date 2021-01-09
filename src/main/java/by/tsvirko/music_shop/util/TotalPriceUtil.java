package by.tsvirko.music_shop.util;

import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.Product;

import java.math.BigDecimal;
import java.util.Map;

public class TotalPriceUtil {
//    public enum TotalPriceEnum {
//        DELETE,
//        ADD
//    }

    public static BigDecimal countPrice(Map<Product, Integer> map) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : map.entrySet()) {
            BigDecimal productPrice = entry.getKey().getPrice();
            total = total.add(productPrice.multiply(new BigDecimal(entry.getValue())));
        }
        return total;
    }
//
//    public static BigDecimal countPrice(Map<Product, Integer> map, TotalPriceEnum param) {
//        BigDecimal price = countPrice(map);
//        BigDecimal productPrice = product.getPrice();
//        switch (param) {
//            case ADD:
//                if (order.getProductIts().contains(product)) {
//                    return price.add(productPrice.multiply(new BigDecimal(amount).subtract(new BigDecimal(1))));
//                } else {
//                    return price.add(productPrice.multiply(new BigDecimal(amount)));
//                }
//            case DELETE:
//                if (order.getProductIts().contains(product)) {
//                    return price.add(productPrice.multiply(new BigDecimal(amount).subtract(new BigDecimal(1))));
//                } else {
//                    return price.add(productPrice.multiply(new BigDecimal(amount)));
//                }
//            default:
//                return price;
//        }
//    }
}
