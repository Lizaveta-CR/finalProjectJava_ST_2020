package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.Producer;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public interface ProducerService extends Service {
    List<Producer> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Producer producer) throws ServicePersistentException;
}
