package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface OrderItemService extends Service {
    void save(OrderItem order) throws ServicePersistentException;

    void save(List<OrderItem> order) throws ServicePersistentException;

    void delete(Integer orderId) throws ServicePersistentException;

    List<Product> readProductsByOrderId(Integer id) throws ServicePersistentException;

    List<OrderItem> buildOrderItems(Map<Product, Byte> map);
}
