package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Order;

import java.util.List;
import java.util.Map;

public interface OrderDAO extends Dao<Integer, Order> {
    List<Order> read() throws PersistentException;

    Map<Integer, List<Order>> read(int offset, int noOfRecords,Integer buyerId) throws PersistentException;
}
