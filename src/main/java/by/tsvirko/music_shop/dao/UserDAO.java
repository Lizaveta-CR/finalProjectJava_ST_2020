package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.User;

import java.util.List;

public interface UserDAO extends Dao<Integer, User> {
    List<User> read() throws PersistentException;
}
