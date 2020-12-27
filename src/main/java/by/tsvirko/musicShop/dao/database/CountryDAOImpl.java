package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.CountryDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CountryDAOImpl extends BaseDAO implements CountryDAO {
    private static final Logger logger = LogManager.getLogger(CountryDAOImpl.class);

    private static final String SQL_READ_COUNTRY_ID_BY_NAME = "SELECT id FROM countries WHERE name = ?";
    private static final String SQL_READ_COUNTRY_NAME_BY_ID = "SELECT name FROM countries WHERE id = ?";

    @Override
    public Integer create(Country entity) throws PersistentException {
        throw new PersistentException("Unable to perform create() operation with Country");
    }

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
}
