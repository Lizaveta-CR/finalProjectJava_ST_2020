package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.CategoryDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.service.CategoryService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.Optional;

public class CategoryServiceImpl extends ServiceImpl implements CategoryService {
    @Override
    public Category getCategory() throws ServicePersistentException {
        try {
            CategoryDAO dao = transaction.createDao(CategoryDAO.class, true);
            Optional<Category> category = dao.read();
            if (category.isPresent()) {
                return category.get();
            } else {
                throw new ServicePersistentException("Empty category");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
