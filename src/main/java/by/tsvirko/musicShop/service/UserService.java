package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public interface UserService extends Service{
    void save(User user) throws ServicePersistentException;
}
