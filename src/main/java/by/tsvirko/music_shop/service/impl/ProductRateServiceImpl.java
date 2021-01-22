package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.ProductRateDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.domain.ProductRate;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.Map;

/**
 * Product rate service
 */
public class ProductRateServiceImpl extends ServiceImpl implements ProductRateService {
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            ProductRateDAO dao = transaction.createDao(ProductRateDAO.class, false);
            dao.delete(identity);
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
    public void save(ProductRate productRate) throws ServicePersistentException {
        try {
            ProductRateDAO dao = transaction.createDao(ProductRateDAO.class, false);
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
            ProductRateDAO dao = transaction.createDao(ProductRateDAO.class, true);
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
