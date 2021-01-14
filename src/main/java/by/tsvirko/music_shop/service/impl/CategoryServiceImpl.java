package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.CategoryDAO;
import by.tsvirko.music_shop.dao.ProductDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Component;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.CategoryService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl extends ServiceImpl implements CategoryService {
    @Override
    public Category getCategory() throws ServicePersistentException {
        try {
            CategoryDAO dao = transaction.createDao(CategoryDAO.class, true);
            ProductDAO productDAO = transaction.createDao(ProductDAO.class, true);
            Optional<Category> optionalCategory = dao.read();
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                List<Product> products = productDAO.read();
                buildListProductCategories(category, products);
                return category;
            } else {
                throw new ServicePersistentException("Empty category");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private void buildListProductCategories(Category category, List<Product> products) {
        for (Product product : products) {
            if (product.getCategory().getId() == category.getId()) {
                category.addProduct(product);
            }
        }
        List<Component> components = category.getComponents();
        int size = components.size();
        for (int i = 0; i < size; i++) {
            Category component = (Category) components.get(i);
            buildListProductCategories(component, products);
        }
    }
}
