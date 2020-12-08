package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.domain.User;

import java.util.List;

public interface UserDAO extends Dao<User> {
    List<User> read();
}
