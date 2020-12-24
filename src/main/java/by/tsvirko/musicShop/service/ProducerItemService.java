package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.OrderItem;
import by.tsvirko.musicShop.domain.ProducerItem;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public interface ProducerItemService extends Service {
    void save(ProducerItem item) throws ServicePersistentException;
}
