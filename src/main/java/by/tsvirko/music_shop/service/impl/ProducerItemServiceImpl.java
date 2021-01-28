package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.CountryDAO;
import by.tsvirko.music_shop.dao.ProducerItemDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Country;
import by.tsvirko.music_shop.domain.Producer;
import by.tsvirko.music_shop.domain.ProducerItem;
import by.tsvirko.music_shop.service.ProducerItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Producer item service
 */
public class ProducerItemServiceImpl extends ServiceImpl implements ProducerItemService {
    private static final Logger logger = LogManager.getLogger(ProducerItemServiceImpl.class);

    /**
     * Saves producer item
     *
     * @param item - producer item to save
     * @throws ServicePersistentException if saving exception occurs
     */
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
                logger.warn("Transaction can not be rollbacked: ", ex.getMessage());
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Reads producer by product identity
     *
     * @param identity - producer identity
     * @return producer corresponding product identity
     * @throws ServicePersistentException if producer is empty
     */
    @Override
    public Producer readProducerByProduct(Integer identity) throws ServicePersistentException {
        try {
            ProducerItemDAO dao = transaction.createDao(ProducerItemDAO.class, true);
            Optional<Producer> producer = dao.readProducerByProduct(identity);
            if (producer.isPresent()) {
                Producer prod = producer.get();
                buildList(prod);
                return prod;
            }
            throw new ServicePersistentException("Empty producer");
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills producer with corresponding fields
     *
     * @param producer - producer to fill with data
     * @throws ServicePersistentException if filling error occurs or empty fields were found
     */
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
