package by.tsvirko.music_shop.service.factory;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.dal.transaction.Transaction;
import by.tsvirko.music_shop.dal.transaction.TransactionFactory;
import by.tsvirko.music_shop.dal.transaction.impl.TransactionFactoryImpl;
import by.tsvirko.music_shop.service.*;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.*;

/**
 * Service factory.
 */
public class ServiceFactoryImpl implements ServiceFactory {
    private TransactionFactory factory;

    public ServiceFactoryImpl() throws ServicePersistentException {
        try {
            this.factory = new TransactionFactoryImpl();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Gets service by it's enum name.
     *
     * @param key - given service interface
     * @return service implementation corresponding input key interface
     * @throws ServicePersistentException if service can not be returned or does not exist
     */
    @Override
    public <Type extends Service> Type getService(ServiceType key) throws ServicePersistentException {
        ServiceImpl service = switchService(key);
        Transaction transaction = factory.createTransaction();
        service.setTransaction(transaction);
        return (Type) service;
    }

    /**
     * Switches service by its enum type.
     *
     * @param key - service type
     * @return ServiceImpl - service corresponding to enum type
     * @throws ServicePersistentException if no service was found
     */
    private ServiceImpl switchService(ServiceType key) throws ServicePersistentException {
        switch (key) {
            case USER:
                return new UserServiceImpl();
            case BUYER:
                return new BuyerServiceImpl();
            case ADDRESS:
                return new AddressServiceImpl();
            case ORDER:
                return new OrderServiceImpl();
            case ORDER_ITEM:
                return new OrderItemServiceImpl();
            case PRODUCER:
                return new ProducerServiceImpl();
            case PRODUCER_ITEM:
                return new ProducerItemServiceImpl();
            case PRODUCT:
                return new ProductServiceImpl();
            case PRODUCT_RATE:
                return new ProductRateServiceImpl();
            case CATEGORY:
                return new CategoryServiceImpl();
            case COUNTRY:
                return new CountryServiceImpl();
            default:
                throw new ServicePersistentException("Service can not be returned");
        }
    }

    /**
     * Closes transaction factory.
     */
    @Override
    public void close() {
        factory.close();
    }

}
