package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.CategoryDAO;
import by.tsvirko.musicShop.dao.ProductDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.domain.Component;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.ProductService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.*;
import java.util.stream.Collectors;

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
