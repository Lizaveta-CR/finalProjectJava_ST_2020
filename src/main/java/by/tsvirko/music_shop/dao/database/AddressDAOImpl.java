package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.AddressDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDAOImpl extends BaseDAO implements AddressDAO {
    private static final Logger logger = LogManager.getLogger(AddressDAOImpl.class);

    private static final String SQL_INSERT_ADDRESS = "INSERT INTO addresses (buyer_id,country_id, city,zip_code,street, apartment_number,house_number) VALUES (?, ?,?,?,?,?,?)";
    private static final String SQL_READ_ALL_ADDRESSES = "SELECT * FROM addresses";
    private static final String SQL_DELETE_ADDRESS = "DELETE FROM addresses WHERE buyer_id = ?";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE addresses SET country_id = ?, city =? ,zip_code=?, street = ?, apartment_number = ?,house_number = ? WHERE buyer_id = ?";
    private static final String SQL_SELECT_ADDRESSES = "SELECT country_id, city,zip_code,street, apartment_number, house_number FROM addresses WHERE buyer_id = ?";

    /**
     * Reads all addresses from 'addresses' table
     *
     * @return addresses list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Address> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ALL_ADDRESSES);
            resultSet = statement.executeQuery();
            List<Address> addresses = new ArrayList<>();
            Address address = null;
            while (resultSet.next()) {
                address = new Address();
                address.setId(resultSet.getInt(Field.BUYER_ID.value()));
                Country country = new Country();
                country.setId(resultSet.getInt(Field.COUNTRY_ID.value()));
                address.setCountry(country);
                address.setCity(resultSet.getString(Field.CITY.value()));
                address.setZipCode(resultSet.getInt(Field.ZIP_CODE.value()));
                address.setStreet(resultSet.getString(Field.STREET.value()));
                address.setApartmentNumber(resultSet.getInt(Field.APARTMENT_NUMBER.value()));
                address.setHouseNumber(resultSet.getInt(Field.HOUSE_NUMBER.value()));
                addresses.add(address);
            }
            logger.debug("Addresses were read");
            return addresses;
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
     * Creates address in database
     *
     * @param entity
     * @return entity key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Address entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ADDRESS);
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getCountry().getId());
            statement.setString(3, entity.getCity());
            statement.setInt(4, entity.getZipCode());
            statement.setString(5, entity.getStreet());
            statement.setInt(6, entity.getApartmentNumber());
            statement.setInt(7, entity.getHouseNumber());
            statement.executeUpdate();

            index = entity.getId();
            logger.debug("Address with id= " + index + " was created");
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
            if (index == null) {
                throw new PersistentException();
            }
            return index;
        }
    }

    @Override
    public Optional<Address> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ADDRESSES);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Address address = null;
            if (resultSet.next()) {
                address = new Address();
                address.setId(identity);
                Country country = new Country();
                country.setId(resultSet.getInt(Field.COUNTRY_ID.value()));
                address.setCountry(country);
                address.setCity(resultSet.getString(Field.CITY.value()));
                address.setZipCode(resultSet.getInt(Field.ZIP_CODE.value()));
                address.setStreet(resultSet.getString(Field.STREET.value()));
                address.setApartmentNumber(resultSet.getInt(Field.APARTMENT_NUMBER.value()));
                address.setHouseNumber(resultSet.getInt(Field.HOUSE_NUMBER.value()));
            }
            logger.debug("Address with id=" + identity + " was read");
            return Optional.ofNullable(address);
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
     * Updates address in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Address entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ADDRESS);
            statement.setInt(1, entity.getCountry().getId());
            statement.setString(2, entity.getCity());
            statement.setInt(3, entity.getZipCode());
            statement.setString(4, entity.getStreet());
            statement.setInt(5, entity.getApartmentNumber());
            statement.setInt(6, entity.getHouseNumber());
            statement.setInt(7, entity.getId());
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
        logger.debug("Address with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes address from 'addresses' table by identity
     *
     * @param identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_ADDRESS);
            statement.setInt(1, identity);
            int num = statement.executeUpdate();

            if (num == 0) {
                throw new PersistentException("Nothing to delete!");
            }
            logger.debug("Address with id= " + identity + " was deleted");
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
