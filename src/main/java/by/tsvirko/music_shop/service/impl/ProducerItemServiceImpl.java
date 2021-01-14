package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.CountryDAO;
import by.tsvirko.music_shop.dao.ProducerItemDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;
import by.tsvirko.music_shop.service.ProducerItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.Optional;

public class ProducerItemServiceImpl extends ServiceImpl implements ProducerItemService {
    @Override
    public void save(ProducerItem item) throws ServicePersistentException {
        try {
            ProducerItemDAO dao = transaction.createDao(ProducerItemDAO.class, false);
            dao.create(item);
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public Optional<Producer> readProducerByProduct(Integer identity) throws ServicePersistentException {
        try {
            ProducerItemDAO dao = transaction.createDao(ProducerItemDAO.class, true);
            Optional<Producer> producer = dao.readProducerByProduct(identity);
            if (producer.isPresent()) {
                buildList(producer.get());
                return producer;
            }
            throw new ServicePersistentException("Empty producer");
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(Producer producer) throws ServicePersistentException {
        try {
            CountryDAO dao = transaction.createDao(CountryDAO.class, true);
            Optional<Country> country = dao.read(producer.getCountry().getId());
            if (country.isPresent()) {
                producer.setCountry(country.get());
            } else {
                throw new ServicePersistentException("Empty country while saving producer");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
