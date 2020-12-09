package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFactoryImpl implements TransactionFactory {
    private static Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);

    private Connection connection;

    /**
     * TransactionFactoryImpl constructor. Gets connection from ConnectionPool class and sets auto commit into false.
     *
     * @throws PersistentException if it is impossible to turn off autocommiting
     */
    public TransactionFactoryImpl() throws PersistentException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException | ConnectionPoolException e) {
            logger.error("It is impossible to turn off autocommiting for database connection", e);
            throw new PersistentException(e);
        }
    }

    /**
     * Creates transaction
     *
     * @return TransactionImpl instance
     * @throws PersistentException
     */
    @Override
    public Transaction createTransaction() {
        return new TransactionImpl(connection);
    }

    /**
     * Closes connection
     *
     * @throws PersistentException if it is impossible to close connection"
     */
    @Override
    public void close() throws PersistentException {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("It is impossible to close connection");
            throw new PersistentException(e);
        }
    }
}
