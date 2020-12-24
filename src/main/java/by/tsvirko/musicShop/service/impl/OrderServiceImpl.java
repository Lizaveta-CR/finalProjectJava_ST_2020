package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.*;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.*;
import by.tsvirko.musicShop.service.OrderService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.*;

public class OrderServiceImpl extends ServiceImpl implements OrderService {
    @Override
    public List<Order> findAll() throws ServicePersistentException {
        OrderDAO dao;
        try {
            dao = transaction.createDao(OrderDAO.class, true);
            List<Order> orders = dao.read();
            buildList(orders);
            return orders;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        OrderDAO dao;
        try {
            dao = transaction.createDao(OrderDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Order order) throws ServicePersistentException {
        OrderDAO dao;
        try {
            dao = transaction.createDao(OrderDAO.class, false);
            if (order.getId() != null) {
                dao.update(order);
            } else {
                order.setId(dao.create(order));
            }
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(List<Order> orders) throws PersistentException {
        OrderItemDAO orderItemDAO = transaction.createDao(OrderItemDAO.class, false);

        Map<Integer, Set<Product>> productOrderMap = new HashMap<>();
        Set<Product> orderProductList;

        Integer identity;

        for (Order item : orders) {
            identity = item.getId();
            orderProductList = productOrderMap.get(identity);
            if (orderProductList == null) {
                orderProductList = new HashSet<>();
                productOrderMap.put(identity, orderProductList);
            }
            List<Product> products = orderItemDAO.readProductsByOrder(identity);
            orderProductList.addAll(products);
            item.setProductIts(orderProductList);
        }
    }
}
