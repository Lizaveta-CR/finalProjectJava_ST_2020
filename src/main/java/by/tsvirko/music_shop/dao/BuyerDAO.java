package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Buyer;

import java.util.List;

public interface BuyerDAO extends Dao<Integer, Buyer> {
    List<Buyer> read() throws PersistentException;
}
