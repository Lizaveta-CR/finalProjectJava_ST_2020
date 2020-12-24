package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.Order;
import by.tsvirko.musicShop.domain.OrderItem;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public interface OrderItemService extends Service {
    void save(OrderItem order) throws ServicePersistentException;
}
