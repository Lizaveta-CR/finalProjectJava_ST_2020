package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

public interface ProducerItemService extends Service {
    void save(ProducerItem item) throws ServicePersistentException;

    Optional<Producer> readProducerByProduct(Integer identity) throws ServicePersistentException;
}
