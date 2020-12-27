package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.AddressDAO;
import by.tsvirko.musicShop.dao.CategoryDAO;
import by.tsvirko.musicShop.dao.CountryDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Address;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.domain.Country;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.AddressService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

public class AddressServiceImpl extends ServiceImpl implements AddressService {
    @Override
    public List<Address> findAll() throws ServicePersistentException {
        try {
            AddressDAO dao = transaction.createDao(AddressDAO.class, true);
            List<Address> addresses = dao.read();
            if (!addresses.isEmpty()) {
                buildList(addresses);
                return addresses;
            } else {
                throw new ServicePersistentException("Empty addresses");
            }
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

    private void buildList(List<Address> addresses) throws ServicePersistentException {
        try {
            CountryDAO countryDAO = transaction.createDao(CountryDAO.class, true);

            Integer identity;
            for (Address address : addresses) {
                identity = address.getCountry().getId();
                if (identity != null) {
                    Optional<Country> country = countryDAO.read(identity);
                    if (country.isPresent()) {
                        address.setCountry(country.get());
                    }
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
