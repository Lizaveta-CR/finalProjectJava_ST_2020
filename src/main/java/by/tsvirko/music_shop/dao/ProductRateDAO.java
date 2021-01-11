package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.ProductRate;

import java.util.Map;


public interface ProductRateDAO extends Dao<Integer, ProductRate> {
    Map<Integer, Integer> countAverageRate() throws PersistentException;
}
