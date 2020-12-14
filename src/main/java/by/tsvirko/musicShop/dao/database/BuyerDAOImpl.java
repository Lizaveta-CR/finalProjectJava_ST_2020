package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.BuyerDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.Address;
import by.tsvirko.musicShop.domain.Buyer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAOImpl extends BaseDao implements BuyerDAO {
    private static Logger logger = LogManager.getLogger(UserDAOImpl.class);
    private static final String SQL_INSERT_BUYER = "INSERT INTO buyers (buyer_id,email, telephone,balance,bonus, enabled) VALUES (?, ?,?,?,?,?)";
    private static final String SQL_UPDATE_BUYER = "UPDATE buyers SET email = ?, telephone =? ,balance=?, bonus = ?, enabled = ? WHERE buyer_id = ?";
    private static final String SQL_DELETE_BUYER = "DELETE FROM buyers WHERE buyer_id = ?";
    private static final String SQL_READ_ALL_BUYERS = "SELECT * FROM buyers";


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
                user.setId(resultSetBuyer.getInt("buyer_id"));
                Address address = new Address();
                address.setId(user.getId());
                user.setAddress(address);
                user.setEmail(resultSetBuyer.getString("email"));
                user.setTelephone(resultSetBuyer.getLong("telephone"));
                user.setBalance(resultSetBuyer.getBigDecimal("balance"));
                user.setBonus(resultSetBuyer.getBigDecimal("bonus"));
                user.setEnabled(resultSetBuyer.getBoolean("enabled"));
                buyers.add(user);
            }
            return buyers;
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                resultSetBuyer.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                statementReadBuyer.close();
            } catch (SQLException | NullPointerException e) {
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
            return index;
        }
    }

    @Override
    public Buyer read(Integer identity) throws PersistentException {
        return null;
    }

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
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_BUYER);
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
    }

    public static void main(String[] args) throws PersistentException, ConnectionPoolException {
        Buyer user = new Buyer();
        user.setId(3);
//        user.setBonus(new BigDecimal(0));
//        user.setEmail("test555");
//        user.setTelephone(1234564L);
//        user.setBalance(new BigDecimal(1111.1));
//        user.setSurname("test");
//        user.setEnabled(Role.BUYER);
        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl(false);
        Transaction transaction = factory.createTransaction();
//        UserDAO dao = transaction.createDao(UserDAO.class);
//        dao.read().forEach(System.out::println);
//        dao.delete(3);
        transaction.commit();
//        System.out.println(integer);
    }
}
