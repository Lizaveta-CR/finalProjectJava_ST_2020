package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Map;

public interface UserService extends Service {
    List<User> findAll() throws ServicePersistentException;

    void delete(Integer identity) throws ServicePersistentException;

    void save(User user) throws ServicePersistentException;

    void updatePassword(User user) throws ServicePersistentException;

    User findByLoginAndPassword(String login, String password) throws ServicePersistentException;

    User findById(Integer identity) throws ServicePersistentException;
}

