package by.tsvirko.music_shop.dal.dao;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Product;

import java.util.List;

public interface ProductDAO extends Dao<Integer, Product> {
    List<Product> read() throws PersistentException;

    List<Product> readProductsByMark(int mark) throws PersistentException;

}
