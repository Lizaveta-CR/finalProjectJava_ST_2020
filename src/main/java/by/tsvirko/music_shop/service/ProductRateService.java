package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.ProductRate;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

public interface ProductRateService extends Service {
    void delete(Integer identity) throws ServicePersistentException;

    void save(ProductRate productRate) throws ServicePersistentException;
}
