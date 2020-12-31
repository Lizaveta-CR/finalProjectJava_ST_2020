package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Producer;

import java.util.List;

public interface ProducerDAO extends Dao<Integer, Producer> {
    List<Producer> read() throws PersistentException;
}
