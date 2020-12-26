package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.domain.Order;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO extends Dao<Integer, Category> {
    Optional<Category> read() throws PersistentException;
}
