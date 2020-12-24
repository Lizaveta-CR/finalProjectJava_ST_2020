package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.ProductRate;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public interface ProductRateService extends Service {
    void delete(Integer identity) throws ServicePersistentException;

    void save(ProductRate productRate) throws ServicePersistentException;
}
