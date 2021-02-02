package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.ProductRate;

import java.util.Map;


public interface ProductRateDAO extends Dao<Integer, ProductRate> {
    Map<Integer, Integer> countAverageRate() throws PersistentException;
}
