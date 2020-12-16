package by.tsvirko.musicShop.dao;

import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.domain.Product;

import java.util.List;

public interface ProductDAO extends Dao<Product> {
    List<Product> read() throws PersistentException;
}
