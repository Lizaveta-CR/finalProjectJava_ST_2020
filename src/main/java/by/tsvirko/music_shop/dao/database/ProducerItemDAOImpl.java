package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.ProducerItemDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProducerItemDAOImpl extends BaseDAO implements ProducerItemDAO {
    private static final Logger logger = LogManager.getLogger(ProducerItemDAOImpl.class);
    private static final String SQL_INSERT_PRODUCER_ITEM = "INSERT INTO producer_items (producer_id, product_id) VALUES (?, ?)";
    private static final String SQL_READ_PRODUCT_BY_PRODUCER = "SELECT pr_it.producer_id, pr.name, pr.country_id FROM producer_items as pr_it" +
            " INNER JOIN producers as pr ON pr_it.producer_id = pr.id WHERE pr_it.product_id = ?";

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
            logger.error("It is impossible co connect to database");
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
     * @return
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
     * @throws PersistentException
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
            logger.error("It is impossible co connect to database");
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
        return null;
    }
}
