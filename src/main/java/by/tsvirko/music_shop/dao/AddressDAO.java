package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;

import java.util.List;

public interface AddressDAO extends Dao<Integer, Address> {
    List<Address> read() throws PersistentException;
}
