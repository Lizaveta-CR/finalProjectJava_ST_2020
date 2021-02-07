package by.tsvirko.music_shop.dal.transaction.impl;

import by.tsvirko.music_shop.dal.dao.DAOType;
import by.tsvirko.music_shop.dal.dao.Dao;
import by.tsvirko.music_shop.dal.dao.database.*;
import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.dal.transaction.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Transaction implementation
 */
public class TransactionImpl implements Transaction {
    private static final Logger logger = LogManager.getLogger(TransactionImpl.class);

    private final Map<DAOType, BaseDAO> classes;

    private Connection connection;

    /**
     * Class constructor.
     *
     * @param connection - received connection
     */
    public TransactionImpl(Connection connection) {
        this.connection = connection;
        classes = new EnumMap<>(DAOType.class);
        classes.put(DAOType.USER, new UserDAOImpl());
        classes.put(DAOType.BUYER, new BuyerDAOImpl());
        classes.put(DAOType.PRODUCT, new ProductDAOImpl());
        classes.put(DAOType.ADDRESS, new AddressDAOImpl());
        classes.put(DAOType.ORDER, new OrderDAOImpl());
        classes.put(DAOType.ORDER_ITEM, new OrderItemDAOImpl());
        classes.put(DAOType.PRODUCER, new ProducerDAOImpl());
        classes.put(DAOType.PRODUCER_ITEM, new ProducerItemDAOImpl());
        classes.put(DAOType.PRODUCT_RATE, new ProductRateDAOImpl());
        classes.put(DAOType.CATEGORY, new CategoryDAOImpl());
        classes.put(DAOType.COUNTRY, new CountryDAOImpl());
    }

    /**
     * Creates Dao
     *
     * @param key          - given dao interface
     * @param isAutoCommit if we need or not autocommit
     * @return BaseDAO with setted connection
     */
    @Override
    public <Type extends Dao<?, ?>> Type createDao(DAOType key, boolean isAutoCommit) throws PersistentException {
        BaseDAO dao = classes.get(key);
        if (dao != null) {
            try {
                connection.setAutoCommit(isAutoCommit);
                dao.setConnection(connection);
                return (Type) dao;
            } catch (SQLException e) {
                logger.error("Commit can not be setted");
            }
        }
        throw new PersistentException("Dao can not be created");
    }

    /**
     * Makes all changes made since the previous
     * commit/rollback permanent
     *
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public void commit() throws PersistentException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to commit transaction", e);
        }
    }

    /**
     * Undoes all changes made in the current transaction
     * and releases any database locks
     *
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public void rollback() throws PersistentException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to rollback transaction", e);
        }
    }
}
