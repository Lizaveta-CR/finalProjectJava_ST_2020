package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.CountryDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for country
 */
public class CountryDAOImpl extends BaseDAO implements CountryDAO {
    private static final Logger logger = LogManager.getLogger(CountryDAOImpl.class);

    private static final String SQL_READ_COUNTRY_ID_BY_NAME = "SELECT id FROM countries WHERE name = ?";
    private static final String SQL_READ_COUNTRY_NAME_BY_ID = "SELECT name FROM countries WHERE id = ?";
    private static final String SQL_READ_COUNTRIES_NAMES = "SELECT name FROM countries";

    @Override
    public Integer create(Country entity) throws PersistentException {
        throw new PersistentException("Unable to perform create() operation with Country");
    }

    /**
     * Reads country by identity
     *
     * @param identity - country identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the country, otherwise return an empty Optional.
     * @throws PersistentException - if database error occurs
     */
    @Override
    public Optional<Country> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_COUNTRY_NAME_BY_ID);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();

            Country country = null;
            if (resultSet.next()) {
                country = new Country();
                country.setName(resultSet.getString(1));
                country.setId(identity);
            }
            logger.debug("Country was read by id=" + identity);
            return Optional.ofNullable(country);
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

    @Override
    public void update(Country entity) throws PersistentException {
        throw new PersistentException("Unable to perform update() operation with Country");
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        throw new PersistentException("Unable to perform delete() operation with Country");
    }

    /**
     * Reads country by name
     *
     * @param name - country name
     * @return If a value is present, and the value matches the given name,
     * return an Optional describing the country, otherwise return an empty Optional.
     * @throws PersistentException if database error occurs
     */
    @Override
    public Optional<Country> readCountryByName(String name) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_COUNTRY_ID_BY_NAME);
            statement.setString(1, name);
            resultSet = statement.executeQuery();

            Country country = null;
            if (resultSet.next()) {
                country = new Country();
                country.setName(name);
                country.setId(resultSet.getInt(1));
            }
            logger.debug("Country was read by name=" + name);
            return Optional.ofNullable(country);
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
     * Reads all country names in database
     *
     * @return list of names
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<String> readNames() throws PersistentException {
        PreparedStatement statementReadBuyer = null;
        ResultSet resultSet = null;
        try {
            statementReadBuyer = connection.prepareStatement(SQL_READ_COUNTRIES_NAMES);
            resultSet = statementReadBuyer.executeQuery();
            List<String> countries = new ArrayList<>();
            while (resultSet.next()) {
                countries.add(resultSet.getString(Field.NAME.value()));
            }
            logger.debug("Countries' names were read");
            return countries;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible to connect to database", e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                if (statementReadBuyer != null) {
                    statementReadBuyer.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }
}
