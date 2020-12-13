package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl extends ServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public void save(User user) throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class);
        } catch (PersistentException e) {
            //TODO: change loger level
            logger.debug("It is impossible to create data access object in " + getClass().getName(), e);
            throw new ServicePersistentException(e);
        }
        try {
            if (user.getId() != null) {
                dao.update(user);
            } else {
                user.setId(dao.create(user));
            }
        } catch (PersistentException e) {
            //TODO: change loger level
            logger.debug("It is impossible to save user", e);
            throw new ServicePersistentException(e);
        }
    }
}
