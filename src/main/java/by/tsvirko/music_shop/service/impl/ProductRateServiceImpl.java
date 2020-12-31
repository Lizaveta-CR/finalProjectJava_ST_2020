package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.ProductRateDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.ProductRate;
import by.tsvirko.music_shop.service.ProductRateService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

public class ProductRateServiceImpl extends ServiceImpl implements ProductRateService {
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            ProductRateDAO dao = transaction.createDao(ProductRateDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
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
            throw new ServicePersistentException(e);
        }
    }
}
