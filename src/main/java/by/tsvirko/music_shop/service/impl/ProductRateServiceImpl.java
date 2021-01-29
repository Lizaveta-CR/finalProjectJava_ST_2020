package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.DAOType;
import by.tsvirko.music_shop.dao.ProductRateDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.ProductRate;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Product rate service
 */
public class ProductRateServiceImpl extends ServiceImpl implements ProductRateService {
    private static final Logger logger = LogManager.getLogger(ProductRateServiceImpl.class);

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            ProductRateDAO dao = transaction.createDao(DAOType.PRODUCT_RATE, false);
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

    @Override
    public void save(ProductRate productRate) throws ServicePersistentException {
        try {
            ProductRateDAO dao = transaction.createDao(DAOType.PRODUCT_RATE, false);
            if (productRate.getId() != null) {
                dao.update(productRate);
            } else {
                productRate.setId(dao.create(productRate));
            }
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
     * Counts average rate(mark) for each product
     *
     * @return Map<Integer, Integer>, where first Integer represents Product id and second-average mark
     * @throws ServicePersistentException if rates are empty
     */
    @Override
    public Map<Integer, Integer> countAverageRate() throws ServicePersistentException {
        try {
            ProductRateDAO dao = transaction.createDao(DAOType.PRODUCT_RATE, true);
            Map<Integer, Integer> map = dao.countAverageRate();
            if (!map.isEmpty()) {
                return map;
            }
            throw new ServicePersistentException("Empty rates");
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
