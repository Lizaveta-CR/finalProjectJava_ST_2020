package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Address;
import by.tsvirko.musicShop.domain.Buyer;

import java.util.List;

public interface AddressDAO extends Dao<Integer, Address> {
    List<Address> read() throws PersistentException;
}
