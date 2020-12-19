package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.ProducerItemDAO;
import by.tsvirko.musicShop.dao.ProductRateDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class ProductRateDAOImpl extends BaseDAO implements ProductRateDAO {
    private static final Logger logger = LogManager.getLogger(ProductRateDAOImpl.class);

    private static final String SQL_INSERT_PRODUCT_RATE = "INSERT INTO product_rates (mark,product_id, buyer_id) VALUES (?, ?,?)";
    private static final String SQL_UPDATE_PRODUCT_RATE = "UPDATE product_rates SET mark = ? WHERE id = ?";
    private static final String SQL_DELETE_PRODUCT_RATE = "DELETE FROM product_rates WHERE id = ?";
    private static final String SQL_READ_ALL_PRODUCTS = "SELECT * FROM products";

    /**
     * Creates product rate in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(ProductRate entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PRODUCT_RATE, Statement.RETURN_GENERATED_KEYS);
            statement.setByte(1, entity.getMark());
            statement.setInt(2, entity.getProduct().getId());
            statement.setInt(3, entity.getBuyer().getId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `users`");
                throw new PersistentException();
            }
            logger.debug("ProductRate with id= " + index + " was created");
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
            return index;
        }
    }

    @Override
    public ProductRate read(Integer identity) throws PersistentException {
        return null;
    }

    /**
     * Updates product rate in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(ProductRate entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_RATE);
            statement.setByte(1, entity.getMark());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
        logger.debug("ProductRate with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes product rate by identity
     *
     * @param identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_PRODUCT_RATE);
            statement.setInt(1, identity);
            statement.executeUpdate();
            logger.debug("ProductRate with id= " + identity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }
}
