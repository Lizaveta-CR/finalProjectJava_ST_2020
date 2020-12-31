package by.tsvirko.music_shop.service;


import by.tsvirko.music_shop.service.exception.ServicePersistentException;

public interface ServiceFactory {
    <Type extends Service> Type getService(Class<Type> key) throws ServicePersistentException;

    void close();
}
