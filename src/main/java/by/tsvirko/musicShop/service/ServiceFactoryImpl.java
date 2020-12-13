package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactoryImpl implements ServiceFactory {
    private static final Logger logger = LogManager.getLogger(ServiceFactoryImpl.class);

    private static final Map<Class<? extends Service>, Class<? extends ServiceImpl>> SERVICES = new ConcurrentHashMap<>();

    static {
        SERVICES.put(UserService.class, UserServiceImpl.class);
    }

    private TransactionFactory factory;

    public ServiceFactoryImpl(TransactionFactory factory) {
        this.factory = factory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Type extends Service> Type getService(Class<Type> key) {
        Class<? extends ServiceImpl> value = SERVICES.get(key);
        if (value != null) {
            try {
//                ClassLoader classLoader = value.getClassLoader();
                Class<?>[] interfaces = {key};
                Transaction transaction = factory.createTransaction();
                ServiceImpl service = value.newInstance();
                service.setTransaction(transaction);
//                InvocationHandler handler = new ServiceInvocationHandlerImpl(service);
//                return (Type) Proxy.newProxyInstance(classLoader, interfaces, handler);
            } catch (InstantiationException | IllegalAccessException e) {
                //TODO:logger
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void close() {
        factory.close();
    }
}
