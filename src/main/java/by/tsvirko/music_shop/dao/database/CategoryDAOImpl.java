package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.CategoryDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CategoryDAOImpl extends BaseDAO implements CategoryDAO {
    private static final Logger logger = LogManager.getLogger(CategoryDAOImpl.class);

    private static final String SQL_READ_ALL_CATEGORIES = "SELECT id,name, parent_id FROM categories";
    private static final String SQL_READ_CATEGORY_NAME = "SELECT name,parent_id FROM categories WHERE id=?";

    @Override
    public Optional<Category> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ALL_CATEGORIES);
            resultSet = statement.executeQuery();

            Map<Integer, Category> categoryMap = new HashMap<>();

            while (resultSet.next()) {
                int id = resultSet.getInt(Field.ID.value());

                //  ----- Child -----
                Category child;
                if (categoryMap.containsKey(id)) {
                    child = categoryMap.get(id);
                } else {
                    child = new Category();
                    categoryMap.put(id, child);
                }
                child.setId(id);
                child.setName(resultSet.getString(Field.NAME.value()));

                // ------ Parent ----
                int parent_id = resultSet.getInt(Field.PARENT_ID.value());
                Category parent;
                if (categoryMap.containsKey(parent_id)) {
                    parent = categoryMap.get(parent_id);
                } else {
                    parent = new Category();
                    categoryMap.put(parent_id, parent);
                    parent.setId(parent_id);
                }
                parent.add(child);
            }
            logger.debug("Categories were read");
            return Optional.ofNullable((Category) categoryMap.get(0).getChild(0));
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    @Override
    public Integer create(Category entity) throws PersistentException {
        throw new PersistentException("Unable to perform create() operation with Category");
    }

    @Override
    public Optional<Category> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_CATEGORY_NAME);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();

            Category category = null;
            if (resultSet.next()) {
                category = new Category();
                category.setId(identity);
                category.setName(resultSet.getString(Field.NAME.value()));
            }
            logger.debug("Category name with id= " + identity + "was read");
            return Optional.ofNullable(category);
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close result set");
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    @Override
    public void update(Category entity) throws PersistentException {
        throw new PersistentException("Unable to perform update() operation with Category");
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        throw new PersistentException("Unable to perform delete(Integer identity) operation with Category");
    }
}
