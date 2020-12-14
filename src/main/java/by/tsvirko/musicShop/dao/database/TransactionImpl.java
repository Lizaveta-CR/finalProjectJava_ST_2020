package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.Dao;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionImpl implements Transaction {
    private static Logger logger = LogManager.getLogger(TransactionImpl.class);

    private static Map<Class<? extends Dao<?>>, Class<? extends BaseDao>> classes = new ConcurrentHashMap<>();

    static {
        classes.put(UserDAO.class, UserDAOImpl.class);
    }

    private Connection connection;

    public TransactionImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates Dao
     * @SuppressWarnings("unchecked") tells the compiler that the programmer believes the code to be safe and won't cause unexpected exceptions
     * @param key - given dao interface
     * @return BaseDAO with setted connection
     * @throws PersistentException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException {
        Class<? extends BaseDao> value = classes.get(key);
        if (value != null) {
            try {
                BaseDao dao = value.newInstance();
                dao.setConnection(connection);
                return (Type) dao;
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("It is impossible to create data access object", e);
                throw new PersistentException(e);
            }
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
