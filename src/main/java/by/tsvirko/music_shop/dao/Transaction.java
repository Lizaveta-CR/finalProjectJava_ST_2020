package by.tsvirko.music_shop.dao;


import by.tsvirko.music_shop.dao.exception.PersistentException;

public interface Transaction {
    <Type extends Dao<?, ?>> Type createDao(Class<Type> key, boolean isAutocommit) throws PersistentException;

    //подтверждает выполнение SQL-запросов
    void commit() throws PersistentException;

    //отменяются действия всех запросов SQL, начиная от последнего вызова commit()
    void rollback() throws PersistentException;
}
