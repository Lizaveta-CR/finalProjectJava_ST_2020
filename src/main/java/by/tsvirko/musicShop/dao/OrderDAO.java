package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Order;

import java.util.List;

public interface OrderDAO extends Dao<Order> {
    List<Order> read() throws PersistentException;
}
