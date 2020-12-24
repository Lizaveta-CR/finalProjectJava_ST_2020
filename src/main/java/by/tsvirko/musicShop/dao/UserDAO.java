package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends Dao<User> {
    Optional<List<User>> read() throws PersistentException;
}
