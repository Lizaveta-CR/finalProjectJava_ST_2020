package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Country;

import java.util.Optional;

public interface CountryDAO extends Dao<Integer, Country> {
    Optional<Country> readCountryByName(String name) throws PersistentException;
}
