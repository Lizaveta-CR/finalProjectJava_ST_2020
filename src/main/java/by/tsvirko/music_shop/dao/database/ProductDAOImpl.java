package by.tsvirko.music_shop.dao.database;

import by.tsvirko.music_shop.dao.ProductDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.Category;
import by.tsvirko.music_shop.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data access object for product
 */
public class ProductDAOImpl extends BaseDAO implements ProductDAO {
    private static final Logger logger = LogManager.getLogger(ProductDAOImpl.class);

    private static final String SQL_INSERT_PRODUCT = "INSERT INTO products (category_id,model, available,description,img, price) VALUES (?, ?,?,?,?,?)";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE products SET category_id = ?, model =? ,available=?, description = ?, img = ?, price = ? WHERE id = ?";
    private static final String SQL_DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private static final String SQL_READ_ALL_PRODUCTS = "SELECT id,category_id,model,available,description,img,price FROM products";
    private static final String SQL_SELECT_PRODUCTS = "SELECT category_id, model,available,description, img, price FROM products WHERE id = ?";

    /**
     * Creates product in database
     *
     * @param entity
     * @return generated key
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(Product entity) throws PersistentException {
        Integer index = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getCategory().getId());
            statement.setString(2, entity.getModel());
            statement.setBoolean(3, entity.getAvailable());
            statement.setString(4, entity.getDescription());
            statement.setString(5, entity.getImageUrl());
            statement.setBigDecimal(6, entity.getPrice());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                index = resultSet.getInt(1);
            } else {
                logger.error("There is no autoincremented index after trying to add record into table `users`");
                throw new PersistentException();
            }
            logger.debug("Product with id= " + index + " was created");
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
        return index;

    }

    /**
     * Reads product by identity
     *
     * @param identity - product identity
     * @return If a value is present, and the value matches the given identity,
     * return an Optional describing the product, otherwise return an empty Optional.
     * @throws PersistentException if database access failed
     */
    @Override
    public Optional<Product> read(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_PRODUCTS);
            statement.setInt(1, identity);
            resultSet = statement.executeQuery();
            Product product = null;
            if (resultSet.next()) {
                product = new Product();
                product.setId(identity);

                Category category = new Category();
                category.setId(resultSet.getInt(Field.CATEGORY_ID.value()));
                product.setCategory(category);

                product.setModel(resultSet.getString(Field.MODEL.value()));
                product.setAvailable(resultSet.getBoolean(Field.AVAILABLE.value()));
                product.setDescription(resultSet.getString(Field.DESCRIPTION.value()));
                product.setImageUrl(resultSet.getString(Field.IMG.value()));
                product.setPrice(resultSet.getBigDecimal(Field.PRICE.value()));
            }
            logger.debug("Product with id=" + identity + " was read");
            return Optional.ofNullable(product);
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

    /**
     * Updates product in database
     *
     * @param entity - Product instance
     * @throws PersistentException if database error occurs
     */
    @Override
    public void update(Product entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_PRODUCT);
            statement.setInt(1, entity.getCategory().getId());
            statement.setString(2, entity.getModel());
            statement.setBoolean(3, entity.getAvailable());
            statement.setString(4, entity.getDescription());
            statement.setString(5, entity.getImageUrl());
            statement.setBigDecimal(6, entity.getPrice());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
        logger.debug("Product with id= " + entity.getId() + " was updated");
    }

    /**
     * Deletes product by identity
     *
     * @param identity - product identity
     * @throws PersistentException if database error occurs
     */
    @Override
    public void delete(Integer identity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_PRODUCT);
            statement.setInt(1, identity);
            int num = statement.executeUpdate();

            if (num == 0) {
                throw new PersistentException("Nothing to delete");
            }
            logger.debug("Product with id= " + identity + " was deleted");
        } catch (SQLException e) {
            throw new PersistentException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
        }
    }

    /**
     * Reads all products from 'products' table
     *
     * @return users list
     * @throws PersistentException if database error occurs
     */
    @Override
    public List<Product> read() throws PersistentException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQL_READ_ALL_PRODUCTS);
            resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            Product product = null;
            while (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt(Field.ID.value()));
                Category category = new Category();
                category.setId(resultSet.getInt(Field.CATEGORY_ID.value()));
                product.setCategory(category);
                product.setModel(resultSet.getString(Field.MODEL.value()));
                product.setAvailable(resultSet.getBoolean(Field.AVAILABLE.value()));
                product.setDescription(resultSet.getString(Field.DESCRIPTION.value()));
                product.setImageUrl(resultSet.getString(Field.IMG.value()));
                product.setPrice(resultSet.getBigDecimal(Field.PRICE.value()));
                products.add(product);
            }
            logger.debug("Products were read");
            return products;
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
}
