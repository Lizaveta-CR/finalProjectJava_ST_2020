package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.*;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.*;
import by.tsvirko.musicShop.service.BuyerService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.*;

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
        OrderItemDAO orderItemDAO = transaction.createDao(OrderItemDAO.class, false);
        List<Order> orderList = orderDAO.read();
        Map<Integer, List<Order>> ordersMap = new HashMap<>();
        List<Order> buyerOrderList;

        Map<Integer, Set<Product>> productOrderMap = new HashMap<>();
        Set<Product> orderProductList;

        Map<Integer, Address> addressMap = new HashMap<>();
        Address buyerAddress;

        Integer buyerIdentity;
        Integer orderIdentity;
        Integer addressIdentity;

        for (Order order : orderList) {
            buyerIdentity = order.getBuyer().getId();
            buyerOrderList = ordersMap.get(buyerIdentity);
            if (buyerOrderList == null) {
                buyerOrderList = new ArrayList<>();
                ordersMap.put(buyerIdentity, buyerOrderList);
            }
            orderIdentity = order.getId();
            orderProductList = productOrderMap.get(orderIdentity);
            if (orderProductList == null) {
                orderProductList = new HashSet<>();
                productOrderMap.put(orderIdentity, orderProductList);
            }
            List<Product> products = orderItemDAO.readProductsByOrder(orderIdentity);
            orderProductList.addAll(products);
            order.setProductIts(orderProductList);

            buyerOrderList.add(order);
        }
        for (Buyer buyer : buyers) {
            buyerIdentity = buyer.getId();
            buyerOrderList = ordersMap.get(buyerIdentity);
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
                        Optional<Address> addressOptional = addressDAO.read(addressIdentity);
                        if (addressOptional.isPresent()) {
                            buyerAddress = addressOptional.get();
                        } else {
                            buyerAddress = new Address();
                            buyerAddress.setId(buyer.getId());
                        }
                        addressMap.put(addressIdentity, buyerAddress);
                    }
                    buyer.setAddress(buyerAddress);
                }
            }
        }
    }
}
