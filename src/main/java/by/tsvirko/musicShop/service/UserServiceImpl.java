package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.exception.PasswordException;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public List<User> findAll() throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class);
            return dao.read();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
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
                if (user.getPassword() != null) {
                    user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
                } else {
                    User oldUser = dao.read(user.getId());
                    user.setPassword(oldUser.getPassword());
                }
                dao.update(user);
            } else {
                //TODO: think about new String()
                user.setPassword(PasswordUtil.hashPassword(new String()));
                user.setId(dao.create(user));
            }
        } catch (PersistentException | PasswordException e) {
            throw new ServicePersistentException(e);
        }
    }
}
