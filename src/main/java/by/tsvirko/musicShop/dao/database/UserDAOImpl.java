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
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends BaseDao implements UserDAO {
    private static Logger logger = LogManager.getLogger(UserDAOImpl.class);
    private static final String SQL_INSERT_USERS = "INSERT INTO users (login, name,surname,password, role) VALUES (?, ?,?,?,?)";
    private static final String SQL_UPDATE_USERS = "UPDATE users SET login = ?, name =? ,surname=?, password = ?, role = ? WHERE id = ?";
    private static final String SQL_DELETE_USERS = "DELETE FROM users WHERE id = ?";
    private static final String SQL_READ_ALL_USERS = "SELECT * FROM users";


    @Override
    public List<User> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ALL_USERS);
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.getByIdentity(resultSet.getInt("role")));
                users.add(user);
            }
            return users;
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
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getName());
            statement.setString(4, entity.getSurname());
            statement.setInt(5, entity.getRole().getIdentity());
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
    public User read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(User entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USERS);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getName());
            statement.setString(4, entity.getSurname());
            statement.setInt(5, entity.getRole().getIdentity());
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
            statement = connection.prepareStatement(SQL_DELETE_USERS);
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
//        User user = new User();
//        user.setLogin("test555");
//        user.setPassword("test555Pass");
//        user.setName("test");
//        user.setSurname("test");
//        user.setRole(Role.BUYER);
        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl(false);
        Transaction transaction = factory.createTransaction();
        UserDAO dao = transaction.createDao(UserDAO.class);
        dao.read().forEach(System.out::println);
        transaction.commit();
//        System.out.println(integer);
    }
}
