package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.OrderDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Data access object for order
 */
public class OrderDAOImpl extends BaseDAO implements OrderDAO {
    private static final Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (buyer_id,date, price) VALUES (?,?,?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET buyer_id = ?, date =? ,price=? WHERE id = ?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
    private static final String SQL_READ_ALL_ORDERS = "SELECT id,buyer_id,date,price FROM orders";
    private static final String SQL_NUMBER_OF_RECORDS = "SELECT FOUND_ROWS()";

    /**
     * Reads all orders from 'orders' table
     *
     * @return orders list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Order> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ALL_ORDERS);
            resultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt(Field.ID.value()));

                Buyer buyer = new Buyer();
                buyer.setId(resultSet.getInt(Field.BUYER_ID.value()));
                order.setBuyer(buyer);

                order.setDate(resultSet.getDate(Field.DATE.value()));
                order.setPrice(resultSet.getBigDecimal(Field.PRICE.value()));
                orders.add(order);
            }
            logger.debug("Orders were read");
            return orders;
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
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
     * Reads all orders from 'orders' table with specified offset and number of records from specific buyer
     *
     * @param offset
     * @param noOfRecords
     * @param buyerId
     * @return Map<Integer, List < Order>>,where Integer represents number of found rows
     * @throws PersistentException if database error occurs
     */
    @Override
    public Map<Integer, List<Order>> read(int offset, int noOfRecords, Integer buyerId) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String SQL_READ_ALL_ORDERS_LIMIT = "SELECT SQL_CALC_FOUND_ROWS id,date,price " +
                    "FROM orders WHERE buyer_id = ? LIMIT " + offset + ", " + noOfRecords + ";";
            statement = connection.prepareStatement(SQL_READ_ALL_ORDERS_LIMIT);
            statement.setInt(1, buyerId);
            resultSet = statement.executeQuery();
            Map<Integer, List<Order>> map = new HashMap<>();
            List<Order> orders = new ArrayList<>();
            Order order = null;
            while (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt(Field.ID.value()));
                order.setDate(resultSet.getDate(Field.DATE.value()));
                order.setPrice(resultSet.getBigDecimal(Field.PRICE.value()));
                orders.add(order);
            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = statement.executeQuery(SQL_NUMBER_OF_RECORDS);
                Integer sqlNoOfRecords = null;
                if (resultSet.next()) {
                    sqlNoOfRecords = resultSet.getInt(1);
                }
                map.put(sqlNoOfRecords, orders);
            }
            logger.debug("Orders were read");
            return map;
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
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
     * Creates order in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Order entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getBuyer().getId());
            if (entity.getDate() != null) {
                statement.setDate(2, new Date(entity.getDate().getTime()));
            } else {
                statement.setNull(2, Types.DATE);
            }
            statement.setBigDecimal(3, entity.getPrice());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `users`");
                throw new PersistentException();
            }
            logger.debug("Order with id= " + index + " was created");
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
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
        return index;
    }

    @Override
    public Optional<Order> read(Integer identity) throws PersistentException {
        return null;
    }

    /**
     * Updates order in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Order entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ORDER);
            statement.setInt(1, entity.getBuyer().getId());
            statement.setDate(2, new Date(entity.getDate().getTime()));
            statement.setBigDecimal(3, entity.getPrice());
            statement.setInt(4, entity.getId());
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
        logger.debug("Order with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes order by identity
     *
     * @param identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER);
            statement.setInt(1, identity);
            int num = statement.executeUpdate();

            if (num == 0) {
                throw new PersistentException("Nothing to delete");
            }
            logger.debug("Order with id= " + identity + " was deleted");
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
}
