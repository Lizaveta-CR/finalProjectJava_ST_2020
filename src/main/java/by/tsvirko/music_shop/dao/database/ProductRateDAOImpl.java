package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.ProductRateDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Data access object for order product rate
 */
public class ProductRateDAOImpl extends BaseDAO implements ProductRateDAO {
    private static final Logger logger = LogManager.getLogger(ProductRateDAOImpl.class);

    private static final String SQL_INSERT_PRODUCT_RATE = "INSERT INTO product_rates (mark,product_id, buyer_id) VALUES (?, ?,?)";
    private static final String SQL_UPDATE_PRODUCT_RATE = "UPDATE product_rates SET mark = ? WHERE id = ?";
    private static final String SQL_DELETE_PRODUCT_RATE = "DELETE FROM product_rates WHERE id = ?";
    private static final String SQL_SELECT_PRODUCT_RATE = "SELECT mark, product_id,buyer_id FROM product_rates WHERE id = ?";
    private static final String SQL_AVG_MARK_PRODUCT = "SELECT AVG(mark) AS avg_mark, product_id FROM product_rates GROUP BY product_id";

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
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT_RATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setByte(1, entity.getMark());
            statement.setInt(2, entity.getProduct().getId());
            statement.setInt(3, entity.getBuyer().getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                throw new PersistentException("There is no autoincremented index after trying to add record into table `product_rates`");
            }
            logger.debug("ProductRate with id= " + index + " was created");
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
        return index;
    }

    /**
     * Reads product rate by identity
     *
     * @param identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the product rate, otherwise return an empty Optional.
     * @throws PersistentException
     */
    @Override
    public Optional<ProductRate> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PRODUCT_RATE);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            ProductRate rate = null;
            if (resultSet.next()) {
                rate = new ProductRate();
                rate.setId(identity);
                rate.setMark(resultSet.getByte(Field.MARK.value()));
                Product product = new Product();
                product.setId(resultSet.getInt(Field.ID.value()));
                rate.setProduct(product);
                Buyer buyer = new Buyer();
                buyer.setId(resultSet.getInt(Field.BUYER_ID.value()));
                rate.setBuyer(buyer);
            }
            logger.debug("ProductRate with id=" + identity + " was read");
            return Optional.ofNullable(rate);
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
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
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    /**
     * Counts average rate(mark) for each product
     *
     * @return Map<Integer, Integer>, where first Integer represents Product id and second-average mark
     */
    @Override
    public Map<Integer, Integer> countAverageRate() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_AVG_MARK_PRODUCT);
            resultSet = statement.executeQuery();
            Map<Integer, Integer> map = new HashMap<>();
            Product product = null;
            while (resultSet.next()) {
                map.put(resultSet.getInt(Field.PRODUCT_ID.value()), resultSet.getInt(Field.AVG_MARK.value()));
            }
            logger.debug("Average prices were counted for products");
            return map;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }
}
