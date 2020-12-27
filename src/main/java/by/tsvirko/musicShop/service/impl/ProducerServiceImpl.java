package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.*;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.domain.Country;
import by.tsvirko.musicShop.domain.Producer;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.ProducerService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.*;

public class ProducerServiceImpl extends ServiceImpl implements ProducerService {
    @Override
    public List<Producer> findAll() throws ServicePersistentException {
        try {
            ProducerDAO dao = transaction.createDao(ProducerDAO.class, true);
            List<Producer> producers = dao.read();
            buildList(producers);
            return producers;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        try {
            ProducerDAO dao = transaction.createDao(ProducerDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Producer producer) throws ServicePersistentException {
        try {
            ProducerDAO dao = transaction.createDao(ProducerDAO.class, false);
            if (producer.getId() != null) {
                dao.update(producer);
            } else {
                dao.create(producer);
            }
            transaction.commit();
        } catch (PersistentException e) {
//            transaction.rollback();
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(List<Producer> producers) throws ServicePersistentException {
        try {
            OrderItemDAO orderItemDAO = transaction.createDao(OrderItemDAO.class, false);
            CountryDAO countryDAO = transaction.createDao(CountryDAO.class, true);
            CategoryDAO categoryDAO = transaction.createDao(CategoryDAO.class, true);

            Map<Integer, Set<Product>> productProducerMap = new HashMap<>();
            Set<Product> orderProductList;

            Integer identity;
            Integer productIdentity;
            for (Producer producer : producers) {
                identity = producer.getId();
                if (identity != null) {
                    Optional<Country> country = countryDAO.read(identity);
                    if (country.isPresent()) {
                        producer.setCountry(country.get());
                    }
                    orderProductList = productProducerMap.get(identity);
                    if (orderProductList == null) {
                        orderProductList = new HashSet<>();
                        productProducerMap.put(identity, orderProductList);
                    }
                    List<Product> products = orderItemDAO.readProductsByOrder(identity);
                    if (!products.isEmpty()) {
                        for (Product product : products) {
                            productIdentity = product.getCategory().getId();
                            if (productIdentity != null) {
                                Optional<Category> category = categoryDAO.read(productIdentity);
                                if (category.isPresent()) {
                                    product.setCategory(category.get());
                                }
                            }
                        }
                        orderProductList.addAll(products);
                        producer.setProducts(orderProductList);
                    }
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

}
