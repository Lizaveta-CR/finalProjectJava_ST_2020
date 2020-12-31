package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Order;

import java.util.List;

public interface OrderDAO extends Dao<Integer, Order> {
    List<Order> read() throws PersistentException;
}
