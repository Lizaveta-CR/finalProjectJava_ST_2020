package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.domain.enums.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UserDAOImpl extends BaseDao implements UserDAO {
    private static Logger logger = LogManager.getLogger(UserDAOImpl.class);
    private static final String SQL_INSERT_USERS = "INSERT INTO musicShop_db.users (login, password, role) VALUES (?, ?,?)";

    @Override
    public List<User> read() {
        return null;
    }

    /**
     * Creates user in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(User entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_USERS, Statement.RETURN_GENERATED_KEYS);
//            statement.setInt(1, entity.getId());
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setInt(3, entity.getRole().getIdentity());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            }
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
            else {
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
    public User read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(User entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }

    public static void main(String[] args) throws PersistentException, ConnectionPoolException {
        User user = new User();
        user.setLogin("test2");
        user.setPassword("test1Pass");
        user.setRole(Role.BUYER);
        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl();
        Transaction transaction = factory.createTransaction();
        UserDAO dao = transaction.createDao(UserDAO.class);
        Integer integer = dao.create(user);
        transaction.commit();
        System.out.println(integer);
    }
}
