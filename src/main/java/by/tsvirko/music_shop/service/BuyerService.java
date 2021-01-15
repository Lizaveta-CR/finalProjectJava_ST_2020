package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface BuyerService extends Service {
    List<Buyer> findAll() throws ServicePersistentException;

    Buyer find(Integer orderAmount) throws ServicePersistentException;

    Map<Integer, List<Buyer>> find(int offset, int noOfRecords) throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Buyer buyer) throws ServicePersistentException;

    void update(Buyer buyer) throws ServicePersistentException;

    Buyer findById(Integer identity) throws ServicePersistentException;
}
