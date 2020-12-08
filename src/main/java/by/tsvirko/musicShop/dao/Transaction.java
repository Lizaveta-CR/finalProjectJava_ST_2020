package by.tsvirko.musicShop.dao;


import by.tsvirko.musicShop.dao.exception.PersistentException;

public interface Transaction {
    <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException;

    //подтверждает выполнение SQL-запросов
    void commit() throws PersistentException;

    //отменяются действия всех запросов SQL, начиная от последнего вызова commit()
    void rollback() throws PersistentException;
}
