package by.tsvirko.music_shop.dao;

import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Product;

import java.util.List;

public interface ProductDAO extends Dao<Integer, Product> {
    List<Product> read() throws PersistentException;

//    String readCategoryChild(Integer category_id, Integer product_id) throws PersistentException;
}