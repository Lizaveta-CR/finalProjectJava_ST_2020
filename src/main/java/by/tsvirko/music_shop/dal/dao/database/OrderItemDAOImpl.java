package by.tsvirko.music_shop.dal.dao.database;

import by.tsvirko.music_shop.dal.dao.OrderItemDAO;
import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for order item
 */
public class OrderItemDAOImpl extends BaseDAO implements OrderItemDAO {
    private static final Logger logger = LogManager.getLogger(OrderItemDAOImpl.class);

    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO order_items (id,product_id, price,amount) VALUES (?, ?,?,?)";
    private static final String SQL_DELETE_ORDER_ITEM = "DELETE FROM order_items WHERE id=?";
    private static final String SQL_DELETE_ORDER_ITEM_PRODUCT = "DELETE FROM order_items WHERE id=? AND product_id=?";
    private static final String SQL_READ_ORDER_ITEM = "SELECT id,product_id,price,amount FROM order_items";

    private static final String SQL_READ_PRODUCTS_BY_ORDER = "SELECT p.id,p.category_id,p.model,p.available,p.description,p.img, p.price FROM products p INNER JOIN order_items o  ON o.product_id = p.id WHERE o.id =?";
    private static final String SQL_UPDATE_ORDER_ITEM = "UPDATE order_items SET price=?,amount=? WHERE id = ? AND  product_id =?";

    /**
     * Reads all orders from 'orders' table
     *
     * @return orders list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<OrderItem> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_ORDER_ITEM)) {
            ResultSet resultSet = statement.executeQuery();
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
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Deletes by orderIdentity and productIdentity
     *
     * @param orderIdentity
     * @param productIdentity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer orderIdentity, Integer productIdentity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_ITEM_PRODUCT)) {
            statement.setInt(1, orderIdentity);
            statement.setInt(2, productIdentity);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        logger.debug("OrderItem with id= " + orderIdentity + "," + productIdentity + " was deleted");
    }

    /**
     * Reads products by order identity
     *
     * @param orderIdentity
     * @return list of products matching order identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Product> readProductsByOrder(Integer orderIdentity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_PRODUCTS_BY_ORDER)) {
            statement.setInt(1, orderIdentity);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            Product product = null;
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt(Field.ID.value()));
                Category category = new Category();
                category.setId(resultSet.getInt(Field.CATEGORY_ID.value()));
                product.setCategory(category);
                product.setModel(resultSet.getString(Field.MODEL.value()));
                product.setAvailable(resultSet.getBoolean(Field.AVAILABLE.value()));
                product.setDescription(resultSet.getString(Field.DESCRIPTION.value()));
                product.setImageUrl(resultSet.getString(Field.IMG.value()));
                product.setPrice(resultSet.getBigDecimal(Field.PRICE.value()));
                products.add(product);
            }
            logger.debug("Products from OrderItem with id=" + orderIdentity + " were read");
            return products;
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER_ITEM)) {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getProduct().getId());
            statement.setBigDecimal(3, entity.getPrice());
            statement.setByte(4, entity.getAmount());
            statement.executeUpdate();

            index = entity.getId();
            logger.debug("OrderItem with id= " + index + " ," + entity.getProduct().getId() + " was created");
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
        return index;
    }

    @Override
    public Optional<OrderItem> read(Integer identity) throws PersistentException {
        throw new PersistentException("Unable read(Integer identity) operation with OrderItem");
    }

    /**
     * Updates order_item in database by order identity
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(OrderItem entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER_ITEM)) {
            statement.setBigDecimal(1, entity.getPrice());
            statement.setByte(2, entity.getAmount());
            statement.setInt(3, entity.getId());
            statement.setInt(4, entity.getProduct().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_ITEM)) {
            statement.setInt(1, orderIdentity);
            int num = statement.executeUpdate();
            if (num == 0) {
                throw new PersistentException("Nothing to delete!");
            }
            logger.debug("OrderItem with id= " + orderIdentity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
