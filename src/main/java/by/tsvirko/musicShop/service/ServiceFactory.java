package by.tsvirko.musicShop.service;


import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key) throws ServicePersistentException;

    void close();
}
