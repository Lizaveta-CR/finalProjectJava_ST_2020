package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.OrderItem;
import by.tsvirko.musicShop.domain.Producer;

import java.util.List;

public interface ProducerDAO extends Dao<Producer> {
    List<Producer> read() throws PersistentException;
}
