package by.tsvirko.music_shop.service.factory;

import by.tsvirko.music_shop.dal.exception.PersistentException;
import by.tsvirko.music_shop.dal.transaction.Transaction;
import by.tsvirko.music_shop.dal.transaction.TransactionFactory;
import by.tsvirko.music_shop.dal.transaction.impl.TransactionFactoryImpl;
import by.tsvirko.music_shop.service.*;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service factory
 */
public class ServiceFactoryImpl implements ServiceFactory {
    private static final Map<ServiceType, ServiceImpl> SERVICES = new ConcurrentHashMap<>();

    /**
     * All services are created in static block
     */
    static {
        SERVICES.put(ServiceType.USER, new UserServiceImpl());
        SERVICES.put(ServiceType.BUYER, new BuyerServiceImpl());
        SERVICES.put(ServiceType.ADDRESS, new AddressServiceImpl());
        SERVICES.put(ServiceType.ORDER, new OrderServiceImpl());
        SERVICES.put(ServiceType.ORDER_ITEM, new OrderItemServiceImpl());
        SERVICES.put(ServiceType.PRODUCER, new ProducerServiceImpl());
        SERVICES.put(ServiceType.PRODUCER_ITEM, new ProducerItemServiceImpl());
        SERVICES.put(ServiceType.PRODUCT, new ProductServiceImpl());
        SERVICES.put(ServiceType.PRODUCT_RATE, new ProductRateServiceImpl());
        SERVICES.put(ServiceType.CATEGORY, new CategoryServiceImpl());
        SERVICES.put(ServiceType.COUNTRY, new CountryServiceImpl());
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl() throws ServicePersistentException {
        try {
            this.factory = new TransactionFactoryImpl();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Gets service by interface name class
     *
     * @param key - given service interface
     * @return service implementation corresponding input key interface
     * @throws ServicePersistentException if service can not be returned or does not exist
     */
    @Override
    public <Type extends Service> Type getService(ServiceType key) throws ServicePersistentException {
        ServiceImpl value = SERVICES.get(key);
        if (value != null) {
            Transaction transaction = factory.createTransaction();
            value.setTransaction(transaction);
            return (Type) value;
        }
        throw new ServicePersistentException("Service can not be returned");
    }

    /**
     * Closes transaction factory
     */
    @Override
    public void close() {
        factory.close();
    }
}
