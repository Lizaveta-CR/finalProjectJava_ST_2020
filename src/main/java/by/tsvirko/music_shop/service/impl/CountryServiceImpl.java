package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.CountryDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.service.CountryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

/**
 * Country service
 */
public class CountryServiceImpl extends ServiceImpl implements CountryService {
    /**
     * Reads countries names
     *
     * @return list of names
     * @throws ServicePersistentException if reading error occurs
     */
    @Override
    public List<String> readNames() throws ServicePersistentException {
        try {
            CountryDAO dao = transaction.createDao(CountryDAO.class, true);
            return dao.readNames();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
