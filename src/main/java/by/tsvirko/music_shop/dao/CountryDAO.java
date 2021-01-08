package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryDAO extends Dao<Integer, Country> {
    Optional<Country> readCountryByName(String name) throws PersistentException;

    List<String> readNames() throws PersistentException;
}
