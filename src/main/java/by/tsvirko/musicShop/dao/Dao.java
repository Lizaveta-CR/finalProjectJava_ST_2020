package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Entity;

/**
 * Interface, which allows to create objects, which are responsible for date extraction
 *
 * @param <Type> - class extending Entity
 */
public interface Dao<Type extends Entity> {
    Integer create(Type entity) throws PersistentException;

    Type read(Integer identity) throws PersistentException;

    void update(Type entity) throws PersistentException;

    void delete(Integer identity) throws PersistentException;
}
