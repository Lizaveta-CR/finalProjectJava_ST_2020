package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.OrderItemDAO;
import by.tsvirko.music_shop.dao.ProductDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.domain.Product;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

import java.util.List;
import java.util.Optional;

public class OrderItemServiceImpl extends ServiceImpl implements OrderItemService {

    @Override
    public void save(OrderItem order) throws ServicePersistentException {
        try {
            OrderItemDAO dao = transaction.createDao(OrderItemDAO.class, false);
            if (order.getId() != null) {
                dao.update(order);
            } else {
                dao.create(order);
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

    @Override
    public void save(List<OrderItem> order) throws ServicePersistentException {
        try {
            OrderItemDAO dao = transaction.createDao(OrderItemDAO.class, false);
            for (OrderItem orderItem : order) {
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

