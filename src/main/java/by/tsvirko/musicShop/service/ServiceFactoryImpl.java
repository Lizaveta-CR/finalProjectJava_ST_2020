package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);

    private static final Map<Class<? extends Service>, ServiceImpl> SERVICES = new ConcurrentHashMap<>();

    static {
        SERVICES.put(UserService.class, new UserServiceImpl());
        SERVICES.put(BuyerService.class, new BuyerServiceImpl());
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
