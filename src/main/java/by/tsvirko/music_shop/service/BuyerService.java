package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface BuyerService extends Service {
    List<Buyer> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Buyer buyer) throws ServicePersistentException;

    void update(Buyer buyer) throws ServicePersistentException;

    Buyer findById(Integer identity) throws ServicePersistentException;
}
