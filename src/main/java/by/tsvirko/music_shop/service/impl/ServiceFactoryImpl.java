package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.Transaction;
import by.tsvirko.music_shop.dao.TransactionFactory;
import by.tsvirko.music_shop.service.*;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);
//TODO: Concurrent скорее излишен, ибо сервисы создаются постоянно?
    private static final Map<Class<? extends Service>, ServiceImpl> SERVICES = new ConcurrentHashMap<>();

    static {
        SERVICES.put(UserService.class, new UserServiceImpl());
        SERVICES.put(BuyerService.class, new BuyerServiceImpl());
        SERVICES.put(AddressService.class, new AddressServiceImpl());
        SERVICES.put(OrderService.class, new OrderServiceImpl());
        SERVICES.put(OrderItemService.class, new OrderItemServiceImpl());
        SERVICES.put(ProducerService.class, new ProducerServiceImpl());
        SERVICES.put(ProducerItemService.class, new ProducerItemServiceImpl());
        SERVICES.put(ProductService.class, new ProductServiceImpl());
        SERVICES.put(ProductRateService.class, new ProductRateServiceImpl());
        SERVICES.put(CategoryService.class, new CategoryServiceImpl());
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    public <Type extends Service> Type getService(Class<Type> key) throws ServicePersistentException {
        ServiceImpl value = SERVICES.get(key);
        if (value != null) {
            Transaction transaction = factory.createTransaction();
            value.setTransaction(transaction);
            return (Type) value;
        }
        logger.error("Can not return service instance");
        throw new ServicePersistentException("Service can not be returned");
    }

    @Override
    public void close() {
        factory.close();
    }
}
