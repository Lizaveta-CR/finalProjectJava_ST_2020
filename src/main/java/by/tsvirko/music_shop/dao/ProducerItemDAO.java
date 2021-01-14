package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;

import java.util.Optional;

public interface ProducerItemDAO extends Dao<Integer, ProducerItem> {
    Optional<Producer> readProducerByProduct(Integer identity) throws PersistentException;
}
