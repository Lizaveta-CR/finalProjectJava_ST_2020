package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.ProducerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

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
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
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
                CountryDAO countryDAO = transaction.createDao(CountryDAO.class, false);
                //if we don't create producer
                Country country = null;
                Integer id = producer.getCountry().getId();
                if (id != null) {
                    Optional<Country> optionalCountry = countryDAO.read(id);
                    if (optionalCountry.isPresent()) {
                        country = optionalCountry.get();
                    } else {
                        throw new ServicePersistentException("Producer can not be saved because of country field");
                    }
                } else {
                    //if we create producer
                    Optional<Country> countryOptional = countryDAO.readCountryByName(producer.getCountry().getName());
                    if (countryOptional.isPresent()) {
                        country = countryOptional.get();
                    } else {
                        throw new ServicePersistentException("Producer can not be saved because of country field");
                    }
                }
                producer.setCountry(country);
                producer.setId(dao.create(producer));
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
    public Producer findById(Integer identity) throws ServicePersistentException {
        try {
            ProducerDAO dao = transaction.createDao(ProducerDAO.class, true);
            Optional<Producer> optionalProducer = dao.read(identity);
            if (optionalProducer.isPresent()) {
                Producer producer = optionalProducer.get();
                buildList(Arrays.asList(producer));
                return producer;
            }
            throw new ServicePersistentException("No such producer");
        } catch (PersistentException | ServicePersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(List<Producer> producers) throws ServicePersistentException {
        try {
            ProducerItemDAO producerItemDAO = transaction.createDao(ProducerItemDAO.class, true);
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
                    List<Product> products = producerItemDAO.readProductsByProducer(identity);
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
                        producer.getProducts().addAll(products);
                    }
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

}
