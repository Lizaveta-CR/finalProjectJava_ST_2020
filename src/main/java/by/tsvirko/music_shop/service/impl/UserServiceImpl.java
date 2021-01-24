package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.UserDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.Role;
import by.tsvirko.music_shop.service.*;
import by.tsvirko.music_shop.service.exception.PasswordException;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.util.PasswordUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User service implementation
 */
public class UserServiceImpl extends ServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    /**
     * Finds all users
     *
     * @return list of users
     * @throws ServicePersistentException if users are empty
     */
    @Override
    public List<User> findAll() throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, true);
            List<User> list = dao.read();
            if (!list.isEmpty()) {
                return list;
            } else {
                throw new ServicePersistentException("List of users is empty");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes user by identity
     * @param identity - user identity
     * @throws ServicePersistentException if user access failed
     */
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked: ", ex.getMessage());
            }
        }
    }

    /**
     * Saves user
     * @param user - entity to save
     * @throws ServicePersistentException if user can not be saved
     */
    @Override
    public void save(User user) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, false);
            if (user.getId() != null) {
                if (user.getPassword() == null) {
                    Optional<User> oldUserOptional = dao.read(user.getId());
                    if (oldUserOptional.isPresent()) {
                        User oldUser = oldUserOptional.get();
                        user.setPassword(oldUser.getPassword());
                    } else {
                        throw new ServicePersistentException("User can not be saved");
                    }
                }
                dao.update(user);
            } else {
                user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
                user.setId(dao.create(user));
            }
            transaction.commit();
        } catch (PersistentException | PasswordException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked: ", ex.getMessage());
            }
        }
    }

    /**
     * Updates user password
     * @param user - entity to update
     * @throws ServicePersistentException if user can not be updated
     */
    @Override
    public void updatePassword(User user) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, false);
            if (user.getPassword() != null && user.getId() != null) {
                user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
                dao.update(user);
            } else {
                throw new ServicePersistentException("User can not be updated");
            }
            transaction.commit();
        } catch (PasswordException | PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked: ", ex.getMessage());
            }
        }
    }

    /**
     * Finds user by login and password
     * @param login - user login
     * @param password - user password
     * @return found user with specified login and password
     * @throws ServicePersistentException if user wasn't found
     */
    @Override
    public User findByLoginAndPassword(String login, String password) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, true);
            if (password != null) {
                Optional<User> optionalUser = dao.read(login, PasswordUtil.hashPassword(password));
                if (optionalUser.isPresent()) {
                    return optionalUser.get();
                }
            }
            throw new ServicePersistentException("No such user");
        } catch (PersistentException | PasswordException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds user by identity
     * @param identity - user identity
     * @return user corresponding to identity
     * @throws ServicePersistentException
     */
    @Override
    public User findById(Integer identity) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, true);
            Optional<User> optionalUser = dao.read(identity);
            if (optionalUser.isPresent()) {
                return optionalUser.get();
            }
            throw new ServicePersistentException("No such user");
        } catch (PersistentException | ServicePersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Finds all users except those whose role is Buyer
     * @return list of users, whose role is not Buyer
     * @throws ServicePersistentException if users can not be obtained
     */
    @Override
    public List<User> findEmployees() throws ServicePersistentException {
        return findAll().stream()
                .filter(user -> !user.getRole().equals(Role.BUYER))
                .collect(Collectors.toList());
    }
}
