package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;

import java.util.List;

public interface AddressDAO extends Dao<Integer, Address> {
    List<Address> read() throws PersistentException;
}
