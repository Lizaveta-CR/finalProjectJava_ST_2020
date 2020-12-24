package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.database.TransactionFactoryImpl;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.*;
import by.tsvirko.musicShop.service.exception.PasswordException;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public List<User> findAll() throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class, true);
            Optional<List<User>> list = dao.read();
            if (list.isPresent()) {
                return list.get();
            } else {
                throw new ServicePersistentException();
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(User user) throws ServicePersistentException {
        UserDAO dao;
        try {
            dao = transaction.createDao(UserDAO.class, false);
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
                //TODO: think about new String()
                user.setPassword(PasswordUtil.hashPassword(new String()));
                user.setId(dao.create(user));
            }
            transaction.commit();
        } catch (PersistentException | PasswordException e) {
            throw new ServicePersistentException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
            ProducerService service = serviceFactory.getService(ProducerService.class);
            service.findAll().forEach(System.out::println);
        } catch (PersistentException | ServicePersistentException e) {
            e.printStackTrace();
        }
    }
}
