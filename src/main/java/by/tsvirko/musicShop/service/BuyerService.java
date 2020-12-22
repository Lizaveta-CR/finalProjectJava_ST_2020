package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public interface BuyerService extends Service {
    List<Buyer> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Buyer buyer) throws ServicePersistentException;

    void update(Buyer buyer) throws ServicePersistentException;
}
