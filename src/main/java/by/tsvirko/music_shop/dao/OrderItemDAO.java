package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;

import java.util.List;

public interface OrderItemDAO extends Dao<Integer, OrderItem> {
    List<OrderItem> read() throws PersistentException;

    void delete(Integer orderIdentity, Integer productIdentity) throws PersistentException;

    List<Product> readProductsByOrder(Integer orderIdentity) throws PersistentException;
}
