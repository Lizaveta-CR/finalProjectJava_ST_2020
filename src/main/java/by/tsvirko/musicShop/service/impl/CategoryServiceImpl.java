package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.CategoryDAO;
import by.tsvirko.musicShop.dao.ProductDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Category;
import by.tsvirko.musicShop.domain.Component;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.CategoryService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

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
                buildList(products, dao);
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
        if (category.getSize() == 0) {
            for (Product product : products) {
                if (product.getCategory().equals(category)) {
                    category.addProduct(product);
                }
            }
        } else {
            List<Component> components = category.getComponents();
            for (int i = 0; i < components.size(); i++) {
                Category component = (Category) components.get(i);
                buildListProductCategories(component, products);
            }
        }
    }

    private void buildList(List<Product> products, CategoryDAO dao) throws ServicePersistentException {
        try {
            Integer identity;
            for (Product product : products) {
                identity = product.getCategory().getId();
                if (identity != null) {
                    Optional<Category> category = dao.read(identity);
                    if (category.isPresent()) {
                        product.setCategory(category.get());
                    }
                }
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
