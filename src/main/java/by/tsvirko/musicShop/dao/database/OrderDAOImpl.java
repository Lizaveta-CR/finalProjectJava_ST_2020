package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.BuyerDAO;
import by.tsvirko.musicShop.dao.OrderDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.domain.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends BaseDAO implements OrderDAO {
    private static final Logger logger = LogManager.getLogger(OrderDAOImpl.class);

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (buyer_id,date, price) VALUES (?,?,?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE orders SET buyer_id = ?, date =? ,price=? WHERE id = ?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM orders WHERE id = ?";
    private static final String SQL_READ_ALL_ORDERS = "SELECT * FROM orders";

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
                order.setId(resultSet.getInt("id"));

                Buyer buyer = new Buyer();
                buyer.setId(resultSet.getInt("buyer_id"));
                order.setBuyer(buyer);

                order.setDate(resultSet.getDate("date"));
                order.setPrice(resultSet.getBigDecimal("price"));
                orders.add(order);
            }
            logger.debug("Orders were read");
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
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
            logger.debug("Order with id= " + index + " was created");
            return index;
        }
    }

    @Override
    public Order read(Integer identity) throws PersistentException {
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
                statement.close();
            } catch (SQLException | NullPointerException e) {
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
        logger.debug("Order with id= " + identity + " was deleted");
    }

    public static void main(String[] args) throws PersistentException, ConnectionPoolException {
//        Order order = new Order();
//        Buyer buyer = new Buyer();
//        buyer.setId(4);
//        order.setId(3);
//        order.setBuyer(buyer);
//        order.setDate(new java.util.Date());
//        order.setPrice(new BigDecimal(667.90));

        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl(false);
        Transaction transaction = factory.createTransaction();
        OrderDAO dao = transaction.createDao(OrderDAO.class);
        dao.read().forEach(System.out::println);
//        dao.delete(3);
//        dao.update(order);
//        Integer integer = dao.create(order);
        transaction.commit();
//        System.out.println(integer);
    }
}
