package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public interface CategoryService extends Service {
    Category getCategory() throws ServicePersistentException;
}
