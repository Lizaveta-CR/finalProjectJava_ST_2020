package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.CategoryDAO;
import by.tsvirko.music_shop.dao.CountryDAO;
import by.tsvirko.music_shop.dao.ProducerItemDAO;
import by.tsvirko.music_shop.dao.ProductDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Category service
 */
public class CategoryServiceImpl extends ServiceImpl implements CategoryService {
    /**
     * Returns category object
     *
     * @return category
     * @throws ServicePersistentException if error while getting occurs
     */
    @Override
    public Category getCategory() throws ServicePersistentException {
        try {
            ProductDAO productDAO = transaction.createDao(ProductDAO.class, true);
            Category simpleCategory = getSimpleCategory();
            List<Product> products = productDAO.read();
            buildProducts(products);
            buildListProductCategories(simpleCategory, products);
            return simpleCategory;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Returns category without products
     *
     * @return category
     * @throws ServicePersistentException if category is empty
     */
    @Override
    public Category getSimpleCategory() throws ServicePersistentException {
        try {
            CategoryDAO dao = transaction.createDao(CategoryDAO.class, true);
            Optional<Category> optionalCategory = dao.read();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                return category;
            } else {
                throw new ServicePersistentException("Empty category");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills products with corresponding fields
     *
     * @param products - products to fill with data
     * @throws ServicePersistentException if some of fields are empty
     */
    private void buildProducts(List<Product> products) throws ServicePersistentException {
        try {
            ProducerItemDAO producerDAO = transaction.createDao(ProducerItemDAO.class, true);
            CountryDAO countryDAO = transaction.createDao(CountryDAO.class, true);
            for (Product product : products) {
                Optional<Producer> producerItem = producerDAO.readProducerByProduct(product.getId());
                if (producerItem.isPresent()) {
                    Producer producer = producerItem.get();
                    product.setProducer(producer);
                    Optional<Country> country = countryDAO.read(producer.getCountry().getId());
                    if (country.isPresent()) {
                        producer.setCountry(country.get());
                    } else {
                        throw new ServicePersistentException("Empty country for producer");
                    }
                } else {
                    throw new ServicePersistentException("Empty producer");
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills category with products with corresponding fields
     *
     * @param products - products to fill  data category with
     * @param category - category to fill with products
     */
    public void buildListProductCategories(Category category, List<Product> products) {
        for (Product product : products) {
            if (product.getCategory().getId() == category.getId()) {
                category.addProduct(product);
            }
        }
        List<Component> components = category.getComponents();
        int size = components.size();
        for (int i = 0; i < size; i++) {
            Category component = (Category) components.get(i);
            buildListProductCategories(component, products);
        }
    }
}
