package by.tsvirko.musicShop.dao;


import by.tsvirko.musicShop.dao.exception.PersistentException;

public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}
