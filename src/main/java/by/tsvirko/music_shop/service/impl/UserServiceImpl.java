package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.UserDAO;
import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.service.*;
import by.tsvirko.music_shop.service.exception.PasswordException;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public List<User> findAll() throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, true);
            List<User> list = dao.read();
            if (!list.isEmpty()) {
                return list;
            } else {
                throw new ServicePersistentException("List is empty");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(User user) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, false);
            if (user.getId() != null) {
                if (user.getPassword() != null) {
                    user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
                } else {
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
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) throws ServicePersistentException {
        try {
            UserDAO dao = transaction.createDao(UserDAO.class, false);
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
}
