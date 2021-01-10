package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface OrderItemService extends Service {
    void save(OrderItem order) throws ServicePersistentException;

    void save(List<OrderItem> order) throws ServicePersistentException;

    List<Product> readProductsByOrderId(Integer id) throws ServicePersistentException;
}
