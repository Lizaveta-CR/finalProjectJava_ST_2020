package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.AddressDAO;
import by.tsvirko.music_shop.dao.OrderItemDAO;
import by.tsvirko.music_shop.dao.ProductDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

/**
 * Order item service
 */
public class OrderItemServiceImpl extends ServiceImpl implements OrderItemService {
    /**
     * Saves order item
     *
     * @param order - order to save
     * @throws ServicePersistentException if saving exception occurs
     */
    @Override
    public void save(OrderItem order) throws ServicePersistentException {
        try {
            OrderItemDAO dao = transaction.createDao(OrderItemDAO.class, false);
            dao.create(order);
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Saves orders
     *
     * @param orders - orders to save
     * @throws ServicePersistentException if saving exception occurs
     */
    @Override
    public void save(List<OrderItem> orders) throws ServicePersistentException {
        try {
            OrderItemDAO dao = transaction.createDao(OrderItemDAO.class, false);
            for (OrderItem orderItem : orders) {
                dao.create(orderItem);
            }
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes order item by identity
     *
     * @param orderId - order item identity
     * @throws ServicePersistentException if deletion error occurs
     */
    @Override
    public void delete(Integer orderId) throws ServicePersistentException {
        OrderItemDAO dao;
        try {
            dao = transaction.createDao(OrderItemDAO.class, false);
            dao.delete(orderId);
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Reads products by order identity
     *
     * @param id - order identity
     * @return list of products
     * @throws ServicePersistentException if products are empty
     */
    @Override
    public List<Product> readProductsByOrderId(Integer id) throws ServicePersistentException {
        try {
            OrderItemDAO dao = transaction.createDao(OrderItemDAO.class, true);
            List<Product> products = dao.readProductsByOrder(id);
            if (!products.isEmpty()) {
                return products;
            } else {
                throw new ServicePersistentException("Empty products");
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}

