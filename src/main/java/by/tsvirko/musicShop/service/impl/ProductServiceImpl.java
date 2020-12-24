package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.ProductDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.ProductService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public class ProductServiceImpl extends ServiceImpl implements ProductService {
    @Override
    public List<Product> findAll() throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class, true);
            return dao.read();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Product product) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class, false);
            if (product.getId() != null) {
                dao.update(product);
            } else {
                product.setId(dao.create(product));
            }
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
