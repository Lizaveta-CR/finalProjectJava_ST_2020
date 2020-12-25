package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Entity;

import java.util.Optional;

/**
 * Interface, which allows to create objects, which are responsible for date extraction
 *
 * @param <Key>-database key type,<Type> - class extending Entity
 */
public interface Dao<Key, Type extends Entity> {
    Integer create(Type entity) throws PersistentException;

    Optional<Type> read(Key identity) throws PersistentException;

    void update(Type entity) throws PersistentException;

    void delete(Key identity) throws PersistentException;
}
