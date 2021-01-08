package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface CountryService extends Service {
    List<String> readNames() throws ServicePersistentException;
}
