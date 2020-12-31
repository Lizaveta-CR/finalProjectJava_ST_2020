package by.tsvirko.music_shop.dao;



public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}
