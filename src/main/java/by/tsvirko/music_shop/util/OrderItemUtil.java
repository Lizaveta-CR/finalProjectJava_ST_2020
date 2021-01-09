package by.tsvirko.music_shop.util;

import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderItemUtil {
    public static List<OrderItem> buildOrderItems(Map<Product, Byte> map) {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = null;
        for (Map.Entry<Product, Byte> entry : map.entrySet()) {
            orderItem = new OrderItem();
            orderItem.setProduct(entry.getKey());
            orderItem.setAmount(entry.getValue());
            orderItem.setPrice(TotalPriceUtil.countPrice(entry));
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
