package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
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
import java.util.Optional;

//TODO: не отлавливать NullPointer, делать через if
public class UserDAOImpl extends BaseDAO implements UserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAOImpl.class);

    private static final String SQL_INSERT_USER = "INSERT INTO users (login, name,surname,password, role) VALUES (?, ?,?,?,?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET login = ?, name =? ,surname=?, password = ?, role = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SQL_READ_ALL_USERS = "SELECT * FROM users";
    private static final String SQL_SELECT_USERS = "SELECT login, name,surname,password, role FROM users WHERE id = ?";

    /**
     * Reads all users from 'users' table
     *
     * @return users list
     * @throws PersistentException if database error occurs
     */
    @Override
    public Optional<List<User>> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ALL_USERS);
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            User user = null;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(Field.ID.value()));
                user.setLogin(resultSet.getString(Field.LOGIN.value()));
                user.setName(resultSet.getString(Field.NAME.value()));
                user.setSurname(resultSet.getString(Field.SURNAME.value()));
                user.setPassword(resultSet.getString(Field.PASSWORD.value()));
                user.setRole(Role.getByIdentity(resultSet.getInt(Field.ROLE.value())));
                users.add(user);
            }
            logger.debug("Users were read");
            return Optional.ofNullable(users);
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                statement.close();
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
    public Integer create(User entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getPassword());
            statement.setInt(5, entity.getRole().getIdentity());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `users`");
                throw new PersistentException();
            }
            logger.debug("User with id= " + index + " was created");
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
            return index;
        }
    }

    /**
     * Reads user by identity
     *
     * @param identity
     * @return
     * @throws PersistentException
     */
    @Override
    public Optional<User> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USERS);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(identity);
                user.setLogin(resultSet.getString(Field.LOGIN.value()));
                user.setName(resultSet.getString(Field.NAME.value()));
                user.setSurname(resultSet.getString(Field.SURNAME.value()));
                user.setPassword(resultSet.getString(Field.PASSWORD.value()));
                user.setRole(Role.getByIdentity(resultSet.getInt(Field.ROLE.value())));
            }
            logger.debug("User with id=" + identity + " was read");
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    /**
     * Updates user in database
     *
     * @param entity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(User entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
            statement.setString(4, entity.getPassword());
            statement.setInt(5, entity.getRole().getIdentity());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
            logger.debug("User with id= " + entity.getId() + " was updated");
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    /**
     * Deletes user by identity
     *
     * @param identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_USER);
            statement.setInt(1, identity);
            statement.executeUpdate();
            logger.debug("User with id= " + identity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }
}
