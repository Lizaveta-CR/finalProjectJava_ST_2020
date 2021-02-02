package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Producer;

import java.util.List;

public interface ProducerDAO extends Dao<Integer, Producer> {
    List<Producer> read() throws PersistentException;
}
