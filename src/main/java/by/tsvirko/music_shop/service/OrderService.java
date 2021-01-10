package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface OrderService extends Service {
    List<Order> findAll() throws ServicePersistentException;

    Map<Integer, List<Order>> find(int offset, int noOfRecords) throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Order order) throws ServicePersistentException;
}

