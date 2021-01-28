package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface ProductService extends Service {
    List<Product> findAll() throws ServicePersistentException;

    List<Product> findNotAvailable() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Product product) throws ServicePersistentException;

    Product findById(Integer identity) throws ServicePersistentException;

    List<Product> readProductsByMark(int mark) throws ServicePersistentException;
}
