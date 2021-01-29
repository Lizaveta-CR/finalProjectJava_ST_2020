package by.tsvirko.music_shop.service;


import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceType;

public interface ServiceFactory {
    <Type extends Service> Type getService(ServiceType type) throws ServicePersistentException;

    void close();
}
