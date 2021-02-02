package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;

import java.util.Optional;

public interface CategoryDAO extends Dao<Integer, Category> {
    Optional<Category> read() throws PersistentException;
}
