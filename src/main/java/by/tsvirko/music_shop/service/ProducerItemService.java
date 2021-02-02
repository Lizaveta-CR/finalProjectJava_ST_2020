package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

public interface ProducerItemService extends Service {
    void save(ProducerItem item) throws ServicePersistentException;

    Producer readProducerByProduct(Integer identity) throws ServicePersistentException;
}
