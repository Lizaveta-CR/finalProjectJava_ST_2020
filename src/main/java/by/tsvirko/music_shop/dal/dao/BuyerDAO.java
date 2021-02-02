package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Buyer;

import java.util.List;
import java.util.Map;

public interface BuyerDAO extends Dao<Integer, Buyer> {
    List<Buyer> read() throws PersistentException;

    Map<Integer, List<Buyer>> read(int offset, int noOfRecords) throws PersistentException;
}
