package by.tsvirko.music_shop.dal.dao.database;

import by.tsvirko.music_shop.dal.dao.AddressDAO;
import by.tsvirko.music_shop.dal.exception.PersistentException;
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

/**
 * Data access object for address
 */
public class AddressDAOImpl extends BaseDAO implements AddressDAO {
    private static final Logger logger = LogManager.getLogger(AddressDAOImpl.class);

    private static final String SQL_INSERT_ADDRESS = "INSERT INTO addresses (buyer_id,country_id, city,zip_code,street, apartment_number,house_number) VALUES (?, ?,?,?,?,?,?)";
    private static final String SQL_READ_ALL_ADDRESSES = "SELECT buyer_id,country_id,city,zip_code,street,apartment_number,house_number FROM addresses";
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
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL_ADDRESSES)) {
            ResultSet resultSet = statement.executeQuery();
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
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Creates address in database
     *
     * @param entity - address
     * @return entity key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Address entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ADDRESS)) {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getCountry().getId());
            statement.setString(3, entity.getCity());
            statement.setInt(4, entity.getZipCode());
            statement.setString(5, entity.getStreet());
            statement.setInt(6, entity.getApartmentNumber());
            statement.setInt(7, entity.getHouseNumber());
            statement.executeUpdate();

            logger.debug("Address with id= " + entity.getId() + " was created");
            return entity.getId();
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Reads address by identity
     *
     * @param identity - address identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the address, otherwise return an empty Optional.
     * @throws PersistentException - if database error occurs
     */
    @Override
    public Optional<Address> read(Integer identity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ADDRESSES)) {
            statement.setInt(1, identity);
            ResultSet resultSet = statement.executeQuery();
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
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Updates address in database
     *
     * @param entity - address to update
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Address entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ADDRESS)) {
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
        }
        logger.debug("Address with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes address from 'addresses' table by identity
     *
     * @param identity -given value
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ADDRESS)) {
            statement.setInt(1, identity);
            int num = statement.executeUpdate();

            if (num == 0) {
                throw new PersistentException("Nothing to delete!");
            }
            logger.debug("Address with id= " + identity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
