package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public List<User> findAll() throws ServicePersistentException {
        return null;
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class);
            dao.delete(identity);
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(User user) throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class);
            if (user.getId() != null) {
                dao.update(user);
            } else {
                user.setId(dao.create(user));
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
