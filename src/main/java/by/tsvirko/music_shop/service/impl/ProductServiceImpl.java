package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Product service
 */
public class ProductServiceImpl extends ServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);

    /**
     * Finds all products
     *
     * @return list of products
     * @throws ServicePersistentException if products are empty
     */
    @Override
    public List<Product> findAll() throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(DAOType.PRODUCT, true);
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

    /**
     * Finds not available products
     *
     * @return list of not available products
     * @throws ServicePersistentException if products are empty
     */
    @Override
    public List<Product> findNotAvailable() throws ServicePersistentException {
        return findAll().stream()
                .filter(product -> product.getAvailable() == false)
                .collect(Collectors.toList());
    }

    /**
     * Deletes product by identity
     *
     * @param identity - products' identity
     * @throws ServicePersistentException if deletion error occurs
     */
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(DAOType.PRODUCT, false);
            dao.delete(identity);
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
     * Saves product
     *
     * @param product - product to save
     * @throws ServicePersistentException if null or empty fields were found
     */
    @Override
    public void save(Product product) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(DAOType.PRODUCT, false);
            if (product.getId() != null) {
                dao.update(product);
            } else {
                product.setId(dao.create(product));
                ProducerItemDAO producerItemDAO = transaction.createDao(DAOType.PRODUCER_ITEM, false);
                producerItemDAO.create(product.getProducer().getId(), product.getId());
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
     * Finds product by identity
     *
     * @param identity - product identity
     * @return - product corresponding to identity
     * @throws ServicePersistentException if product  corresponding to identity does not exist
     */
    @Override
    public Product findById(Integer identity) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(DAOType.PRODUCT, true);
            Optional<Product> optionalProduct = dao.read(identity);
            if (optionalProduct.isPresent()) {
                return optionalProduct.get();
            }
            throw new ServicePersistentException("No such product");
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Returns products with specified mark
     *
     * @param mark - product mark
     * @return category, whose products equals mark
     */
    public List<Product> readProductsByMark(int mark) throws ServicePersistentException {
        try {
            ProductDAO dao = transaction.createDao(DAOType.PRODUCT, true);
            List<Product> products = dao.readProductsByMark(mark);
            if (!products.isEmpty()) {
                buildList(products);
                return products;
            }
            throw new ServicePersistentException("Products with mark=" + mark + " weren't found");
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills products with corresponding fields
     *
     * @param products - products to fill with data
     * @throws ServicePersistentException if filling error occurs
     */
    private void buildList(List<Product> products) throws ServicePersistentException {
        try {
            CategoryDAO categoryDAO = transaction.createDao(DAOType.CATEGORY, true);
            ProducerItemDAO producerDAO = transaction.createDao(DAOType.PRODUCER_ITEM, true);
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
