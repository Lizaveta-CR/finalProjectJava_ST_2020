package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface AddressService extends Service {
    List<Address> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    //TODO: в 1 метод
    void save(Address address) throws ServicePersistentException;

    void update(Address address) throws ServicePersistentException;

    Address findById(Integer id) throws ServicePersistentException;
}

