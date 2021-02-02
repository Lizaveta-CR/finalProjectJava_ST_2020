package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.OrderService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.helper.TotalPriceHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class OrderServiceImpl extends ServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);

    /**
     * Finds all orders
     *
     * @return list of orders
     * @throws ServicePersistentException if reading error occurs
     */
    @Override
    public List<Order> findAll() throws ServicePersistentException {
        try {
            OrderDAO dao = transaction.createDao(DAOType.ORDER, true);
            List<Order> orders = dao.read();
            buildList(orders);
            return orders;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Reads all orders with specified offset and number of records
     *
     * @param offset
     * @param noOfRecords
     * @return Map<Integer, List < Order>>,where Integer represents number of found rows
     * @throws ServicePersistentException if finding error occurs
     */
    @Override
    public Map<Integer, List<Order>> find(int offset, int noOfRecords, Integer buyerId) throws ServicePersistentException {
        try {
            OrderDAO dao = transaction.createDao(DAOType.ORDER, true);
            Map<Integer, List<Order>> map = dao.read(offset, noOfRecords, buyerId);
            return map;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes order  by identity
     *
     * @param identity - order identity
     * @throws ServicePersistentException if deletion error occurs
     */
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        OrderDAO dao;
        try {
            dao = transaction.createDao(DAOType.ORDER, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked: ", ex.getMessage());
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Saves order
     *
     * @param order - order to save
     * @throws ServicePersistentException if saving exception occurs
     */
    @Override
    public void save(Order order) throws ServicePersistentException {
        try {
            OrderDAO orderDAO = transaction.createDao(DAOType.ORDER, false);
            if (order.getId() != null) {
                orderDAO.update(order);
            } else {
                if (order.getDate() == null) {
                    Date now = new Date();
                    order.setDate(now);
                }
                Buyer buyer = order.getBuyer();
                if (buyer != null) {
                    BigDecimal buyerBalance = buyer.getBalance();
                    BigDecimal orderPrice = order.getPrice();
                    if (buyerBalance.compareTo(orderPrice) == -1) {
                        throw new ServicePersistentException("No money");
                    } else {
                        buyer.setBalance(buyerBalance.subtract(orderPrice));
                        buyer.addBonus(new TotalPriceHelper().countBonus(order));
                        BuyerDAO buyerDAO = transaction.createDao(DAOType.BUYER, false);
                        buyerDAO.update(buyer);
                    }
                    order.setId(orderDAO.create(order));
                } else {
                    throw new ServicePersistentException("Buyer is empty");
                }
            }
            transaction.commit();
        } catch (PersistentException e) {
            try {
                transaction.rollback();
            } catch (PersistentException ex) {
                logger.warn("Transaction can not be rollbacked: ", ex.getMessage());
            }
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills orders with corresponding fields
     *
     * @param orders - orders to fill with data
     * @throws ServicePersistentException if filling error occurs
     */
    private void buildList(List<Order> orders) throws PersistentException {
        OrderItemDAO orderItemDAO = transaction.createDao(DAOType.ORDER_ITEM, true);
        CategoryDAO categoryDAO = transaction.createDao(DAOType.CATEGORY, true);

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
