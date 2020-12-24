package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.AddressDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Address;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.service.AddressService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public class AddressServiceImpl extends ServiceImpl implements AddressService {
    @Override
    public List<Address> findAll() throws ServicePersistentException {
        AddressDAO dao;
        try {
            dao = transaction.createDao(AddressDAO.class, false);
            return dao.read();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        AddressDAO dao;
        try {
            dao = transaction.createDao(AddressDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Address address) throws ServicePersistentException {
        AddressDAO dao;
        try {
            dao = transaction.createDao(AddressDAO.class, false);
            if (address.getId() != null) {
                dao.update(address);
            } else {
                address.setId(dao.create(address));
            }
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
