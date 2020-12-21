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

    private static Map<Class<? extends Dao<?>>, BaseDAO> classes = new ConcurrentHashMap<>();

    static {
        classes.put(UserDAO.class, new UserDAOImpl());
        classes.put(BuyerDAO.class, new BuyerDAOImpl());
        classes.put(ProductDAO.class, new ProductDAOImpl());
        classes.put(AddressDAO.class, new AddressDAOImpl());
        classes.put(OrderDAO.class, new OrderDAOImpl());
        classes.put(OrderItemDAO.class, new OrderItemDAOImpl());
        classes.put(ProducerDAO.class, new ProducerDAOImpl());
        classes.put(ProducerItemDAO.class, new ProducerItemDAOImpl());
        classes.put(ProductRateDAO.class, new ProductRateDAOImpl());
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
        BaseDAO dao = classes.get(key);
        if (dao != null) {
            dao.setConnection(connection);
            return (Type) dao;
        }
        return null;
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
