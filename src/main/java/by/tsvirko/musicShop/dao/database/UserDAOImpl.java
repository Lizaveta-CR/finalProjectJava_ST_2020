package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.UserDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.User;

import java.util.List;

public class UserDAOImpl extends BaseDao implements UserDAO {
    @Override
    public List<User> read() {
        return null;
    }

    @Override
    public Integer create(User entity) throws PersistentException {
        return null;
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
}
