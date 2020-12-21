package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.*;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static final Logger logger = LogManager.getLogger(TransactionImpl.class);

    private static final Map<Class<? extends Dao<?>>, BaseDAO> CLASSES = new ConcurrentHashMap<>();

    static {
        CLASSES.put(UserDAO.class, new UserDAOImpl());
        CLASSES.put(BuyerDAO.class, new BuyerDAOImpl());
        CLASSES.put(ProductDAO.class, new ProductDAOImpl());
        CLASSES.put(AddressDAO.class, new AddressDAOImpl());
        CLASSES.put(OrderDAO.class, new OrderDAOImpl());
        CLASSES.put(OrderItemDAO.class, new OrderItemDAOImpl());
        CLASSES.put(ProducerDAO.class, new ProducerDAOImpl());
        CLASSES.put(ProducerItemDAO.class, new ProducerItemDAOImpl());
        CLASSES.put(ProductRateDAO.class, new ProductRateDAOImpl());
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates Dao
     *
     * @param key - given dao interface
     * @return BaseDAO with setted connection
     */
    @Override
    public <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException {
        BaseDAO dao = CLASSES.get(key);
        if (dao != null) {
            dao.setConnection(connection);
            return (Type) dao;
        }
        logger.error("Dao can not be created");
        throw new PersistentException("No dao instance");
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
            logger.error("It is impossible to commit transaction", e);
            throw new PersistentException(e);
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
            logger.error("It is impossible to rollback transaction", e);
            throw new PersistentException(e);
        }
    }
}
