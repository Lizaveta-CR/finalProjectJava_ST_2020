package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.ProducerItemDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.ProducerItem;

public class ProducerItemDAOImpl extends BaseDAO implements ProducerItemDAO {
    @Override
    public Integer create(ProducerItem entity) throws PersistentException {
        return null;
    }

    @Override
    public ProducerItem read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(ProducerItem entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }
}
