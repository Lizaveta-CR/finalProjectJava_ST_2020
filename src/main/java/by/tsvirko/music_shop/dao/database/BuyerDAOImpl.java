package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.BuyerDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Order;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

/**
 * Data access object for buyer
 */
public class BuyerDAOImpl extends BaseDAO implements BuyerDAO {
    private static final Logger logger = LogManager.getLogger(BuyerDAOImpl.class);

    private static final String SQL_INSERT_BUYER = "INSERT INTO buyers (buyer_id,email, telephone,balance,bonus, enabled) VALUES (?, ?,?,?,?,?)";
    private static final String SQL_UPDATE_BUYER = "UPDATE buyers SET email = ?, telephone =? ,balance=?, bonus = ?, enabled = ? WHERE buyer_id = ?";
    private static final String SQL_DELETE_BUYER = "DELETE FROM buyers WHERE buyer_id = ?";
    private static final String SQL_READ_ALL_BUYERS = "SELECT buyer_id, email, telephone, balance, bonus, enabled FROM buyers";
    private static final String SQL_SELECT_BUYERS = "SELECT email, telephone,balance,bonus, enabled FROM buyers WHERE buyer_id = ?";
    private static final String SQL_NUMBER_OF_RECORDS = "SELECT FOUND_ROWS()";

    /**
     * Reads all buyers from 'buyers' table
     *
     * @return buyers list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Buyer> read() throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_READ_ALL_BUYERS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Buyer> buyers = new ArrayList<>();
            Buyer user = null;
            while (resultSet.next()) {
                user = new Buyer();
                user.setId(resultSet.getInt(Field.BUYER_ID.value()));
                Address address = new Address();
                address.setId(user.getId());
                user.setAddress(address);
                user.setEmail(resultSet.getString(Field.EMAIL.value()));
                user.setTelephone(resultSet.getLong(Field.TELEPHONE.value()));
                user.setBalance(resultSet.getBigDecimal(Field.BALANCE.value()));
                user.setBonus(resultSet.getBigDecimal(Field.BONUS.value()));
                user.setEnabled(resultSet.getBoolean(Field.ENABLED.value()));
                buyers.add(user);
            }
            logger.debug("Buyers were read");
            return buyers;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Reads all buyers from 'buyers' table with specified offset and number of records
     *
     * @param offset
     * @param noOfRecords
     * @return Map<Integer, List < Order>>,where Integer represents number of found rows
     * @throws PersistentException if database error occurs
     */
    @Override
    public Map<Integer, List<Buyer>> read(int offset, int noOfRecords) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            final String SQL_READ_ALL_ORDERS_LIMIT = "SELECT SQL_CALC_FOUND_ROWS buyer_id, email, telephone, balance, bonus, enabled"
                    + " FROM buyers LIMIT " + offset + ", " + noOfRecords + ";";
            statement = connection.prepareStatement(SQL_READ_ALL_ORDERS_LIMIT);
            resultSet = statement.executeQuery();
            Map<Integer, List<Buyer>> map = new HashMap<>();
            List<Buyer> buyers = new ArrayList<>();
            Buyer buyer = null;
            while (resultSet.next()) {
                buyer = new Buyer();
                buyer.setId(resultSet.getInt(Field.BUYER_ID.value()));
                Address address = new Address();
                address.setId(buyer.getId());
                buyer.setAddress(address);
                buyer.setEmail(resultSet.getString(Field.EMAIL.value()));
                buyer.setTelephone(resultSet.getLong(Field.TELEPHONE.value()));
                buyer.setBalance(resultSet.getBigDecimal(Field.BALANCE.value()));
                buyer.setBonus(resultSet.getBigDecimal(Field.BONUS.value()));
                buyer.setEnabled(resultSet.getBoolean(Field.ENABLED.value()));
                buyers.add(buyer);
            }
            if (resultSet != null) {
                resultSet.close();
                resultSet = statement.executeQuery(SQL_NUMBER_OF_RECORDS);
                Integer sqlNoOfRecords = null;
                if (resultSet.next()) {
                    sqlNoOfRecords = resultSet.getInt(1);
                }
                map.put(sqlNoOfRecords, buyers);
            }
            logger.debug("Orders were read");
            return map;
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
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

    /**
     * Creates user in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Buyer entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_BUYER)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getEmail());
            statement.setLong(3, entity.getTelephone());
            statement.setBigDecimal(4, entity.getBalance());
            statement.setBigDecimal(5, entity.getBonus());
            statement.setBoolean(6, entity.getEnabled());
            statement.executeUpdate();
            logger.debug("Buyer with id= " + entity.getId() + " was created");
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
        return entity.getId();
    }

    /**
     * Reads buyer by identity
     *
     * @param identity - country identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the buyer, otherwise return an empty Optional.
     * @throws PersistentException - if database error occurs
     */
    @Override
    public Optional<Buyer> read(Integer identity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BUYERS)) {
            statement.setInt(1, identity);
            ResultSet resultSet = statement.executeQuery();
            Buyer buyer = null;
            if (resultSet.next()) {
                buyer = new Buyer();
                buyer.setId(identity);
                buyer.setEmail(resultSet.getString(Field.EMAIL.value()));
                buyer.setTelephone(resultSet.getLong(Field.TELEPHONE.value()));
                buyer.setBalance(resultSet.getBigDecimal(Field.BALANCE.value()));
                buyer.setBonus(resultSet.getBigDecimal(Field.BONUS.value()));
                buyer.setEnabled(resultSet.getBoolean(Field.ENABLED.value()));
            }
            logger.debug("Buyer with id=" + identity + " was read");
            return Optional.ofNullable(buyer);
        } catch (SQLException e) {
            throw new PersistentException("It is impossible co connect to database", e);
        }
    }

    /**
     * Updates buyer in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Buyer entity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BUYER)) {
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getTelephone());
            statement.setBigDecimal(3, entity.getBalance());
            statement.setBigDecimal(4, entity.getBonus());
            statement.setBoolean(5, entity.getEnabled());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
        logger.debug("Buyer with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes buyer from 'buyers' table by identity
     *
     * @param identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BUYER)) {
            statement.setInt(1, identity);
            int num = statement.executeUpdate();
            if (num == 0) {
                throw new PersistentException("Nothing to delete!");
            }
            logger.debug("Buyer with id= " + identity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        }
    }
}
