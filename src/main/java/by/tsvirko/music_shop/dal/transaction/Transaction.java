package by.tsvirko.music_shop.dal.transaction;


import by.tsvirko.music_shop.dal.dao.DAOType;
import by.tsvirko.music_shop.dal.dao.Dao;
import by.tsvirko.music_shop.dal.exception.PersistentException;

/**
 * Transaction interface
 */
public interface Transaction {
    <Type extends Dao<?, ?>> Type createDao(DAOType daoType, boolean isAutocommit) throws PersistentException;

    //подтверждает выполнение SQL-запросов
    void commit() throws PersistentException;

    //отменяются действия всех запросов SQL, начиная от последнего вызова commit()
    void rollback() throws PersistentException;
}
