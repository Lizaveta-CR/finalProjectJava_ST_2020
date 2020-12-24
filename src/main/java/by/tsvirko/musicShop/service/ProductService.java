package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public interface ProductService extends Service {
    List<Product> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Product product) throws ServicePersistentException;
}
