package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.OrderItemDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.OrderItem;
import by.tsvirko.musicShop.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl extends BaseDAO implements OrderItemDAO {
    private static final Logger logger = LogManager.getLogger(OrderItemDAOImpl.class);

    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO order_items (id,product_id, price,amount) VALUES (?, ?,?,?)";
    private static final String SQL_DELETE_ORDER_ITEM = "DELETE FROM order_items WHERE id=?";
    private static final String SQL_DELETE_ORDER_ITEM_PRODUCT = "DELETE FROM order_items WHERE id=? AND product_id=?";
    private static final String SQL_READ_ORDER_ITEM = "SELECT* FROM order_items";
    private static final String SQL_UPDATE_ORDER_ITEM = "UPDATE order_items SET price=?,amount=? WHERE id = ? AND  product_id =?";

    /**
     * Reads all orders from 'orders' table
     *
     * @return orders list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<OrderItem> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ORDER_ITEM);
            resultSet = statement.executeQuery();
            List<OrderItem> orders = new ArrayList<>();
            OrderItem order = null;
            while (resultSet.next()) {
                order = new OrderItem();
                order.setId(resultSet.getInt(Field.ID.value()));

                Product product = new Product();
                product.setId(resultSet.getInt(Field.PRODUCT_ID.value()));
                order.setProduct(product);

                order.setPrice(resultSet.getBigDecimal(Field.PRICE.value()));
                order.setAmount(resultSet.getByte(Field.AMOUNT.value()));
                orders.add(order);
            }
            logger.debug("OrderItems were read");
            return orders;
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
        }
    }

    /**
     * Deletes by orderIdentity and productIdentity
     *
     * @param orderIdentity
     * @param productIdentity
     * @throws PersistentException
     */
    @Override
    public void delete(Integer orderIdentity, Integer productIdentity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER_ITEM_PRODUCT);
            statement.setInt(1, orderIdentity);
            statement.setInt(2, productIdentity);
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
        logger.debug("OrderItem with id= " + orderIdentity + "," + productIdentity + " was deleted");
    }

    /**
     * Creates order_item in database
     *
     * @param entity
     * @return entities' orders' key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(OrderItem entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ORDER_ITEM);
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getProduct().getId());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setByte(4, entity.getAmount());
            statement.executeUpdate();

            index = entity.getId();
            logger.debug("OrderItem with id= " + index + " ," + entity.getProduct().getId() + " was created");
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
            return index;
        }
    }

    @Override
    public OrderItem read(Integer identity) throws PersistentException {
        return null;
    }

    /**
     * Updates order_item in database by order identity
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(OrderItem entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ORDER_ITEM);
            statement.setBigDecimal(1, entity.getPrice());
            statement.setByte(2, entity.getAmount());
            statement.setInt(3, entity.getId());
            statement.setInt(4, entity.getProduct().getId());
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
        logger.debug("OrderItem with id= " + entity.getId() + ", " + entity.getProduct().getId() + "was updated");
    }

    /**
     * Deletes order_item by order identity
     *
     * @param orderIdentity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer orderIdentity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ORDER_ITEM);
            statement.setInt(1, orderIdentity);
            statement.executeUpdate();
            logger.debug("OrderItem with id= " + orderIdentity + " was deleted");
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
