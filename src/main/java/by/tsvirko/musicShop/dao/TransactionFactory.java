package by.tsvirko.musicShop.dao;



public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}
