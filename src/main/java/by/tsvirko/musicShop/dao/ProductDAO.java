package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.domain.Product;

import java.util.List;

public interface ProductDAO extends Dao<Integer, Product> {
    List<Product> read() throws PersistentException;

//    String readCategoryChild(Integer category_id, Integer product_id) throws PersistentException;
}
