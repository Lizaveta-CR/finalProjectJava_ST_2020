package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Product> findNotAvailable() throws ServicePersistentException {
        return findAll().stream()
                .filter(product -> product.getAvailable() == false)
                .collect(Collectors.toList());
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
                ProducerItemDAO producerItemDAO = transaction.createDao(ProducerItemDAO.class, false);
                producerItemDAO.create(product.getProducer().getId(), product.getId());
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

    @Override
    public Product findById(Integer identity) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(ProductDAO.class, true);
            Optional<Product> optionalProduct = dao.read(identity);
            if (optionalProduct.isPresent()) {
                return optionalProduct.get();
            }
            throw new ServicePersistentException("No such product");
        } catch (PersistentException | ServicePersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(List<Product> products) throws ServicePersistentException {
        try {
            CategoryDAO categoryDAO = transaction.createDao(CategoryDAO.class, true);
            ProducerItemDAO producerDAO = transaction.createDao(ProducerItemDAO.class, true);
            Integer identity;
            for (Product product : products) {
                identity = product.getCategory().getId();
                Optional<Producer> producerItem = producerDAO.readProducerByProduct(product.getId());
                if (producerItem.isPresent()) {
                    product.setProducer(producerItem.get());
                }
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
