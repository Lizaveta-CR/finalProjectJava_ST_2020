package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.OrderItem;

import java.util.List;

public interface OrderItemDAO extends Dao<OrderItem> {
    List<OrderItem> read() throws PersistentException;

    void delete(Integer orderIdentity, Integer productIdentity) throws PersistentException;
}
