package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.ProducerItemDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for order producer item
 */
public class ProducerItemDAOImpl extends BaseDAO implements ProducerItemDAO {
    private static final Logger logger = LogManager.getLogger(ProducerItemDAOImpl.class);
    private static final String SQL_INSERT_PRODUCER_ITEM = "INSERT INTO producer_items (producer_id, product_id) VALUES (?, ?)";
    private static final String SQL_READ_PRODUCT_BY_PRODUCER = "SELECT pr_it.producer_id, pr.name, pr.country_id FROM producer_items as pr_it" +
            " INNER JOIN producers as pr ON pr_it.producer_id = pr.id WHERE pr_it.product_id = ?";
    private static final String SQL_READ_PRODUCTS_BY_PRODUCER = "SELECT p.id,p.category_id,p.model,p.available,p.description,p.img, p.price FROM products p INNER JOIN producer_items pr_it  ON pr_it.product_id = p.id WHERE pr_it.producer_id =?";

    /**
     * Creates producerItem in database
     *
     * @param entity
     * @return null
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(ProducerItem entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PRODUCER_ITEM);
            statement.setInt(1, entity.getProducer().getId());
            statement.setInt(2, entity.getProduct().getId());
            statement.executeUpdate();

            logger.debug("ProducerItem with id= " + entity.getProducer().getId() + ", " + entity.getProduct().getId() + " was created");
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database",e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
        return null;
    }

    @Override
    public Optional<ProducerItem> read(Integer identity) throws PersistentException {
        throw new PersistentException("Unable to perform read() operation with ProducerItem");
    }

    @Override
    public void update(ProducerItem entity) throws PersistentException {
        throw new PersistentException("Unable to perform update() operation with ProducerItem");
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        throw new PersistentException("Unable to perform delete() operation with ProducerItem");
    }

    /**
     * Reads producer by product identity
     *
     * @param identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the producer, otherwise return an empty Optional.
     * @throws PersistentException
     */
    @Override
    public Optional<Producer> readProducerByProduct(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_PRODUCT_BY_PRODUCER);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Producer producer = null;
            while (resultSet.next()) {
                producer = new Producer();
                producer.setId(resultSet.getInt(Field.PRODUCER_ID.value()));
                producer.setName(resultSet.getString(Field.NAME.value()));
                Country country = new Country();
                country.setId(resultSet.getInt(Field.COUNTRY_ID.value()));
                producer.setCountry(country);
            }
            logger.debug("Producer from ProducerItem with product id=" + identity + " was read");
            return Optional.ofNullable(producer);
        } catch (SQLException e) {
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
     * Creates producerItem in database
     *
     * @param producerIdentity
     * @param productIdentity
     * @return null
     * @throws PersistentException if database error occurs
     */
    @Override
    public Integer create(Integer producerIdentity, Integer productIdentity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PRODUCER_ITEM);
            statement.setInt(1, producerIdentity);
            statement.setInt(2, productIdentity);
            statement.executeUpdate();

            logger.debug("ProducerItem with id= " + producerIdentity + ", " + productIdentity + " was created");
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database",e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
        return null;
    }

    /**
     * Reads products by producer
     *
     * @param identity - producer identity
     * @return list of products matching producer
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Product> readProductsByProducer(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_PRODUCTS_BY_PRODUCER);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
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
            logger.debug("Products from ProductItem with product id=" + identity + " were read");
            return products;
        } catch (SQLException e) {
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
}
