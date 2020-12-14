package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.domain.User;

import java.util.List;

public interface BuyerDAO extends Dao<Buyer> {
    List<Buyer > read() throws PersistentException;

}
