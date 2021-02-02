package by.tsvirko.music_shop.dal.transaction;


/**
 * Transaction factory interface
 */
public interface TransactionFactory {
    Transaction createTransaction();

    void close();
}
