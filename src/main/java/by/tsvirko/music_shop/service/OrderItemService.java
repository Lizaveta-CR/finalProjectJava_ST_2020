package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

public interface OrderItemService extends Service {
    void save(OrderItem order) throws ServicePersistentException;
}
