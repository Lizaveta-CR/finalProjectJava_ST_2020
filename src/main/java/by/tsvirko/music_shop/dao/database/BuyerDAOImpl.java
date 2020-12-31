package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.BuyerDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: remove NullPointer
public class BuyerDAOImpl extends BaseDAO implements BuyerDAO {
    private static final Logger logger = LogManager.getLogger(BuyerDAOImpl.class);

    private static final String SQL_INSERT_BUYER = "INSERT INTO buyers (buyer_id,email, telephone,balance,bonus, enabled) VALUES (?, ?,?,?,?,?)";
    private static final String SQL_UPDATE_BUYER = "UPDATE buyers SET email = ?, telephone =? ,balance=?, bonus = ?, enabled = ? WHERE buyer_id = ?";
    private static final String SQL_DELETE_BUYER = "DELETE FROM buyers WHERE buyer_id = ?";
    private static final String SQL_READ_ALL_BUYERS = "SELECT * FROM buyers";
    private static final String SQL_SELECT_BUYERS = "SELECT email, telephone,balance,bonus, enabled FROM buyers WHERE buyer_id = ?";

    /**
     * Reads all buyers from 'buyers' table
     *
     * @return buyers list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Buyer> read() throws PersistentException {
        PreparedStatement statementReadBuyer = null;
        ResultSet resultSetBuyer = null;
        try {
            statementReadBuyer = connection.prepareStatement(SQL_READ_ALL_BUYERS);
            resultSetBuyer = statementReadBuyer.executeQuery();
            List<Buyer> buyers = new ArrayList<>();
            Buyer user = null;
            while (resultSetBuyer.next()) {
                user = new Buyer();
                user.setId(resultSetBuyer.getInt(Field.BUYER_ID.value()));
                Address address = new Address();
                address.setId(user.getId());
                user.setAddress(address);
                user.setEmail(resultSetBuyer.getString(Field.EMAIL.value()));
                user.setTelephone(resultSetBuyer.getLong(Field.TELEPHONE.value()));
                user.setBalance(resultSetBuyer.getBigDecimal(Field.BALANCE.value()));
                user.setBonus(resultSetBuyer.getBigDecimal(Field.BONUS.value()));
                user.setEnabled(resultSetBuyer.getBoolean(Field.ENABLED.value()));
                buyers.add(user);
            }
            logger.debug("Buyers were read");
            return buyers;
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                if (resultSetBuyer != null) {
                    resultSetBuyer.close();
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

    /**
     * Creates user in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Buyer entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_BUYER, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getEmail());
            statement.setLong(3, entity.getTelephone());
            statement.setBigDecimal(4, entity.getBalance());
            statement.setBigDecimal(5, entity.getBonus());
            statement.setBoolean(6, entity.getEnabled());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `users`");
                throw new PersistentException();
            }
            logger.debug("Buyer with id= " + index + " was created");
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
        return index;
    }

    @Override
    public Optional<Buyer> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_BUYERS);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
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
     * Updates buyer in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Buyer entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_BUYER);
            statement.setString(1, entity.getEmail());
            statement.setLong(2, entity.getTelephone());
            statement.setBigDecimal(3, entity.getBalance());
            statement.setBigDecimal(4, entity.getBonus());
            statement.setBoolean(5, entity.getEnabled());
            statement.setInt(6, entity.getId());
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
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_BUYER);
            statement.setInt(1, identity);
            statement.executeUpdate();
            logger.debug("Buyer with id= " + identity + " was deleted");
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
