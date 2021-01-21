package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;
import by.tsvirko.music_shop.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProducerItemDAO extends Dao<Integer, ProducerItem> {
    Optional<Producer> readProducerByProduct(Integer identity) throws PersistentException;

    Integer create(Integer producerIdentity, Integer productIdentity) throws PersistentException;

    List<Product> readProductsByProducer(Integer identity) throws PersistentException;
}
