package by.tsvirko.music_shop.dal.transaction.impl;

import by.tsvirko.music_shop.dal.transaction.Transaction;
import by.tsvirko.music_shop.dal.transaction.TransactionFactory;
import by.tsvirko.music_shop.dal.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.dal.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Transaction factory implementation
 */
public class TransactionFactoryImpl implements TransactionFactory {
    private static final Logger logger = LogManager.getLogger(TransactionFactoryImpl.class);

    private Connection connection;

    /**
     * TransactionFactoryImpl constructor. Gets connection from ConnectionPool class and sets auto commit.
     *
     * @throws PersistentException if it is impossible to turn off autocommiting
     */
    public TransactionFactoryImpl() throws PersistentException {
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
        } catch (SQLException | ConnectionPoolException e) {
            throw new PersistentException("It is impossible to turn off autocommiting for database connection",e);
        }
    }

    /**
     * Creates transaction
     *
     * @return TransactionImpl instance
     */
    @Override
    public Transaction createTransaction() {
        return new TransactionImpl(connection);
    }

    /**
     * Closes connection
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("It is impossible to close connection");
        }
    }
}
