package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.ProductRateDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.ProductRate;
import by.tsvirko.musicShop.service.ProductRateService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

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
