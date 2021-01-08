package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.CategoryDAO;
import by.tsvirko.music_shop.dao.ProductDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.*;

public class ProductServiceImpl extends ServiceImpl implements ProductService {
    @Override
    public List<Product> findAll() throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class, true);
            List<Product> products = dao.read();
            if (!products.isEmpty()) {
                buildList(products);
                return products;
            }
            throw new ServicePersistentException("Empty products");
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
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
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
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(List<Product> products) throws ServicePersistentException {
        try {
            CategoryDAO categoryDAO = transaction.createDao(CategoryDAO.class, true);

            Integer identity;
            for (Product product : products) {
                identity = product.getCategory().getId();
                if (identity != null) {
                    Optional<Category> category = categoryDAO.read(identity);
                    if (category.isPresent()) {
                        product.setCategory(category.get());
                    }
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
