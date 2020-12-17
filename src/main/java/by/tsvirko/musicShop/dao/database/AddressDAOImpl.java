package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.AddressDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.Address;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAOImpl extends BaseDAO implements AddressDAO {
    private static final Logger logger = LogManager.getLogger(AddressDAOImpl.class);

    private static final String SQL_INSERT_ADDRESS = "INSERT INTO addresses (buyer_id,country_id, city,zip_code,street, apartment_number,house_number) VALUES (?, ?,?,?,?,?,?)";
    private static final String SQL_READ_COUNTRY_ID_BY_NAME = "SELECT id FROM countries WHERE name = ?";
    private static final String SQL_READ_COUNTRY_NAME_BY_ID = "SELECT name FROM countries WHERE id = ?";
    private static final String SQL_READ_ALL_ADDRESSES = "SELECT * FROM addresses";
    private static final String SQL_DELETE_ADDRESS = "DELETE FROM addresses WHERE buyer_id = ?";
    private static final String SQL_UPDATE_ADDRESS = "UPDATE addresses SET country_id = ?, city =? ,zip_code=?, street = ?, apartment_number = ?,house_number = ? WHERE buyer_id = ?";

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
                address.setId(resultSet.getInt("buyer_id"));
                address.setCountry((String) readCountry("name", resultSet.getInt("country_id")));
                address.setCity(resultSet.getString("city"));
                address.setZipCode(resultSet.getInt("zip_code"));
                address.setStreet(resultSet.getString("street"));
                address.setApartment_number(resultSet.getInt("apartment_number"));
                address.setHouse_number(resultSet.getInt("house_number"));
                addresses.add(address);
            }
            logger.debug("Addresses were read");
            return addresses;
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
     * Creates address in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Address entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_ADDRESS);
            statement.setInt(1, entity.getId());
            statement.setInt(2, (Integer) readCountry("id", entity.getCountry()));
            statement.setString(3, entity.getCity());
            statement.setInt(4, entity.getZipCode());
            statement.setString(5, entity.getStreet());
            statement.setInt(6, entity.getApartment_number());
            statement.setInt(7, entity.getHouse_number());
            statement.executeUpdate();

            index = entity.getId();
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
            logger.debug("Address with id= " + index + " was created");
            return index;
        }
    }

    @Override
    public Address read(Integer identity) throws PersistentException {
        return null;
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
            statement.setInt(1, (Integer) readCountry("id", entity.getCountry()));
            statement.setString(2, entity.getCity());
            statement.setInt(3, entity.getZipCode());
            statement.setString(4, entity.getStreet());
            statement.setInt(5, entity.getApartment_number());
            statement.setInt(6, entity.getHouse_number());
            statement.setInt(7, entity.getId());
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
        logger.debug("Address with id= " + identity + " was deleted");
    }

    /**
     * Reads from countries table by given parameters
     *
     * @param param - what we want to read, value - parameter that helps up to search
     * @return
     * @throws PersistentException if database error occurs
     */
    private <T extends Object> Object readCountry(String param, Object value) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            switch (param) {
                case "id":
                    statement = connection.prepareStatement(SQL_READ_COUNTRY_ID_BY_NAME);
                    statement.setString(1, (String) value);
                    resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                    break;
                case "name":
                    statement = connection.prepareStatement(SQL_READ_COUNTRY_NAME_BY_ID);
                    statement.setInt(1, (Integer) value);
                    resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getString(1);
                    }
                    break;
            }
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
        return null;
    }

    public static void main(String[] args) throws PersistentException, ConnectionPoolException {
        Address address = new Address();
        address.setId(4);
        address.setCountry("Belarus");
        address.setCity("Minsk");
        address.setZipCode(220005);
        address.setStreet("Lenina");
        address.setApartment_number(13);
        address.setHouse_number(9);

        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl(false);
        Transaction transaction = factory.createTransaction();
        AddressDAO dao = transaction.createDao(AddressDAO.class);
        dao.read().forEach(System.out::println);
//        dao.delete(4);
//        dao.update(address);
//        Integer integer = dao.create(address);
        transaction.commit();
//        System.out.println(integer);
    }
}
