package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public interface UserService extends Service {
    List<User> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(User user) throws ServicePersistentException;
}
