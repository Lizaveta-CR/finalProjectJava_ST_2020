package by.tsvirko.music_shop.dal.dao.database;

import by.tsvirko.music_shop.dal.dao.ProducerDAO;
import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for order producer
 */
public class ProducerDAOImpl extends BaseDAO implements ProducerDAO {
    private static final Logger logger = LogManager.getLogger(ProducerDAOImpl.class);

    private static final String SQL_INSERT_PRODUCER = "INSERT INTO producers (name, country_id) VALUES (?, ?)";
    private static final String SQL_DELETE_PRODUCER = "DELETE FROM producers WHERE id=?";
    private static final String SQL_READ_PRODUCERS = "SELECT id,name,country_id FROM producers";
    private static final String SQL_UPDATE_PRODUCER = "UPDATE producers SET name=?,country_id=? WHERE id = ?";
    private static final String SQL_SELECT_PRODUCER = "SELECT name,country_id FROM producers WHERE id=?";

    /**
     * Creates producer in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Producer entity) throws PersistentException {
        Integer index = null;
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getCountry().getId());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `users`");
                throw new PersistentException();
            }
            logger.debug("Producer with id= " + index + " was created");
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
        return index;
    }

    /**
     * Reads producer by identity
     *
     * @param identity - producers' identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the producer, otherwise return an empty Optional.
     * @throws PersistentException
     */
    @Override
    public Optional<Producer> read(Integer identity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRODUCER)
        ) {
            statement.setInt(1, identity);
            ResultSet resultSet = statement.executeQuery();
            Producer producer = null;
            if (resultSet.next()) {
                producer = new Producer();
                producer.setId(identity);
                producer.setName(resultSet.getString(Field.NAME.value()));
                Country country = new Country();
                country.setId(resultSet.getInt(Field.COUNTRY_ID.value()));
                producer.setCountry(country);
            }
            logger.debug("Producer with id=" + identity + " was read");
            return Optional.ofNullable(producer);
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Updates producer in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Producer entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCER)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getCountry().getId());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        logger.debug("Producer with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes producer by identity
     *
     * @param identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRODUCER)) {
            statement.setInt(1, identity);
            int num = statement.executeUpdate();

            if (num == 0) {
                throw new PersistentException("Nothing to delete");
            }
            logger.debug("Producer with id= " + identity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }

    /**
     * Reads all producers from 'producers' table
     *
     * @return producers list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Producer> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_PRODUCERS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Producer> producers = new ArrayList<>();
            Producer producer = null;

            while (resultSet.next()) {
                producer = new Producer();
                producer.setId(resultSet.getInt(Field.ID.value()));
                producer.setName(resultSet.getString(Field.NAME.value()));
                Country country = new Country();
                country.setId(resultSet.getInt(Field.COUNTRY_ID.value()));
                producer.setCountry(country);
                producers.add(producer);
            }
            logger.debug("Producers were read");
            return producers;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }
}
