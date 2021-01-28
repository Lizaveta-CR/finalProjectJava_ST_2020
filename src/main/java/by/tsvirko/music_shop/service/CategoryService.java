package by.tsvirko.music_shop.service;

import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;

public interface CategoryService extends Service {
    Category getCategory() throws ServicePersistentException;

    Category getSimpleCategory() throws ServicePersistentException;

    void buildListProductCategories(Category category, List<Product> products);
}
