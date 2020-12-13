package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;

import java.util.List;

public interface UserDAO extends Dao<User> {
    Integer create(User entity) throws PersistentException;

    void update(User entity) throws PersistentException;

    List<User> read();
}
