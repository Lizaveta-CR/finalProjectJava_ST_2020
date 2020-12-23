package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.*;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Address;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.domain.Order;
import by.tsvirko.musicShop.domain.Product;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerServiceImpl extends ServiceImpl implements BuyerService {
    @Override
    public List<Buyer> findAll() throws ServicePersistentException {
        BuyerDAO dao;
        try {
            dao = transaction.createDao(BuyerDAO.class, true);
            List<Buyer> buyers = dao.read();
            buildList(buyers);
            return buyers;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        BuyerDAO dao;
        try {
            dao = transaction.createDao(BuyerDAO.class, false);
            dao.delete(identity);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void save(Buyer buyer) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class, false);
            dao.create(buyer);
            transaction.commit();
        } catch (PersistentException e) {
//            transaction.rollback();
            throw new ServicePersistentException(e);
        }
    }

    @Override
    public void update(Buyer buyer) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class, false);
            dao.update(buyer);
            transaction.commit();
        } catch (PersistentException e) {
//            transaction.rollback();
            throw new ServicePersistentException(e);
        }
    }

    private void buildList(List<Buyer> buyers) throws PersistentException {
        AddressDAO addressDAO = transaction.createDao(AddressDAO.class, false);
        OrderDAO orderDAO = transaction.createDao(OrderDAO.class, false);
//        ProductDAO productDAO = transaction.createDao(ProductDAO.class, false);
        List<Order> orderList = orderDAO.read();
        Map<Integer, List<Order>> ordersMap = new HashMap<>();
        List<Order> buyerOrderList;

        Map<Integer, Address> addressMap = new HashMap<>();
        Address buyerAddress;

//        List<Product> productList = productDAO.read();
//        Map<Integer, List<Product>> productMap = new HashMap<>();
//        List<Product> orderProductList;

        Integer identity;
        Integer addressIdentity;
        for (Order order : orderList) {
            identity = order.getBuyer().getId();
            buyerOrderList = ordersMap.get(identity);
            if (buyerOrderList == null) {
                buyerOrderList = new ArrayList<>();
                ordersMap.put(identity, buyerOrderList);
            }
            buyerOrderList.add(order);
        }
        for (Buyer buyer : buyers) {
            identity = buyer.getId();
            buyerOrderList = ordersMap.get(identity);
            if (buyerOrderList != null) {
                for (Order order : buyerOrderList) {
                    order.setBuyer(buyer);
                    buyer.addOrder(order);
                }
            }
            if (buyer.getAddress() != null) {
                addressIdentity = buyer.getAddress().getId();
                if (addressIdentity != null) {
                    buyerAddress = addressMap.get(addressIdentity);
                    if (buyerAddress == null) {
                        buyerAddress = addressDAO.read(addressIdentity);
                        addressMap.put(addressIdentity, buyerAddress);
                    }
                    buyer.setAddress(buyerAddress);
                }
            }

        }
    }
}
