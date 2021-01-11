package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.ProductRate;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.Map;

public interface ProductRateService extends Service {
    void delete(Integer identity) throws ServicePersistentException;

    void save(ProductRate productRate) throws ServicePersistentException;

    Map<Integer, Integer> countAverageRate() throws ServicePersistentException;
}
