package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

public interface CategoryService extends Service {
    Category getCategory() throws ServicePersistentException;

    Category getSimpleCategory() throws ServicePersistentException;
}
