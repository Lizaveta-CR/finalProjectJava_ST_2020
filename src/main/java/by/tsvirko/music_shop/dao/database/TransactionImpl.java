package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Transaction implementation
 */
public class TransactionImpl implements Transaction {
    private static final Logger logger = LogManager.getLogger(TransactionImpl.class);

    private static final Map<Class<? extends Dao<?, ?>>, BaseDAO> CLASSES = new ConcurrentHashMap<>();

    /**
     * All dao classes are created in static block
     */
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
        CLASSES.put(CategoryDAO.class, new CategoryDAOImpl());
        CLASSES.put(CountryDAO.class, new CountryDAOImpl());
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates Dao
     *
     * @param key          - given dao interface
     * @param isAutoCommit if we need or not autocomming
     * @return BaseDAO with setted connection
     */
    @Override
    public <Type extends Dao<?, ?>> Type createDao(Class<Type> key, boolean isAutoCommit) throws PersistentException {
        BaseDAO dao = CLASSES.get(key);
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
