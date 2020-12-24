package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.Order;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public interface OrderService extends Service {
    List<Order> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Order order) throws ServicePersistentException;
}

