package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

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
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
            }
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Order order) throws ServicePersistentException {
        try {
            OrderDAO orderDAO = transaction.createDao(OrderDAO.class, false);
            if (order.getId() != null) {
                orderDAO.update(order);
            } else {
//                BuyerDAO buyerDAO = transaction.createDao(BuyerDAO.class, false);
//                Integer buyerIdentity = order.getBuyer().getId();
                //TODO: как быть с бонусом? У покупателя вычесть стоимость товара
//                Optional<Buyer> buyer = buyerDAO.read(buyerIdentity);
                order.setId(orderDAO.create(order));
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

    private void buildList(List<Order> orders) throws PersistentException {
        OrderItemDAO orderItemDAO = transaction.createDao(OrderItemDAO.class, false);
        CategoryDAO categoryDAO = transaction.createDao(CategoryDAO.class, true);

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
            if (!products.isEmpty()) {
                Integer prodIdentity;
                for (Product product : products) {
                    prodIdentity = product.getCategory().getId();
                    if (prodIdentity != null) {
                        Optional<Category> category = categoryDAO.read(prodIdentity);
                        if (category.isPresent()) {
                            product.setCategory(category.get());
                        }
                    }
                }
                orderProductList.addAll(products);
                item.setProductIts(orderProductList);
            }
        }
    }
}
