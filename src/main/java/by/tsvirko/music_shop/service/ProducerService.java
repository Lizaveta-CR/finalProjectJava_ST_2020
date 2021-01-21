package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface ProducerService extends Service {
    List<Producer> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(Producer producer) throws ServicePersistentException;

    Producer findById(Integer identity) throws ServicePersistentException;
}
