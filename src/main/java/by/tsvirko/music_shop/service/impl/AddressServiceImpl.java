package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.AddressDAO;
import by.tsvirko.music_shop.dao.BuyerDAO;
import by.tsvirko.music_shop.dao.CountryDAO;
import by.tsvirko.music_shop.dao.database.TransactionImpl;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Address;
import by.tsvirko.music_shop.domain.Buyer;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Address service
 */
public class AddressServiceImpl extends ServiceImpl implements AddressService {
    private static final Logger logger = LogManager.getLogger(AddressServiceImpl.class);

    /**
     * Finds all addresses
     *
     * @return list of addresses
     * @throws ServicePersistentException if addresses are empty
     */
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

    /**
     * Deletes address by identity
     *
     * @param identity - address identity
     * @throws ServicePersistentException if deletion error occurs
     */
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        AddressDAO dao;
        try {
            dao = transaction.createDao(AddressDAO.class, false);
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
     * Saves address
     *
     * @param address - address to save
     * @throws ServicePersistentException if saving exception occurs
     */
    @Override
    public void save(Address address) throws ServicePersistentException {
        try {
            Country country = address.getCountry();
            if (country.getId() == null) {
                CountryDAO countryDAO = transaction.createDao(CountryDAO.class, true);
                Optional<Country> optionalCountry = countryDAO.readCountryByName(country.getName());
                if (optionalCountry.isPresent()) {
                    address.setCountry(optionalCountry.get());
                }
            }
            AddressDAO dao = transaction.createDao(AddressDAO.class, false);
            dao.create(address);
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
     * Updates address
     *
     * @param address - address to update
     * @throws ServicePersistentException if updating error occurs
     */
    @Override
    public void update(Address address) throws ServicePersistentException {
        try {
            Country country = address.getCountry();
            CountryDAO countryDAO = transaction.createDao(CountryDAO.class, true);
            Optional<Country> optionalCountry = countryDAO.readCountryByName(country.getName());
            if (optionalCountry.isPresent()) {
                address.setCountry(optionalCountry.get());
            }
            AddressDAO dao = transaction.createDao(AddressDAO.class, false);
            dao.update(address);
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
     * Finds address by identity
     *
     * @param id - address identity
     * @return - address corresponding to identity
     * @throws ServicePersistentException if address  corresponding to identity does not exist
     */
    @Override
    public Address findById(Integer id) throws ServicePersistentException {
        try {
            AddressDAO dao = transaction.createDao(AddressDAO.class, true);
            Optional<Address> optionalAddress = dao.read(id);
            if (optionalAddress.isPresent()) {
                Address address = optionalAddress.get();
                buildList(Arrays.asList(address));
                return address;
            }
            throw new ServicePersistentException("No such address");
        } catch (PersistentException | ServicePersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills addresses with corresponding fields
     *
     * @param addresses - addresses to fill with data
     * @throws ServicePersistentException if filling error occurs
     */
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
