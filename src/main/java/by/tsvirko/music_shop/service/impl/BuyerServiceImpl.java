package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.*;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.*;
import by.tsvirko.music_shop.service.BuyerService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Buyer service
 */
public class BuyerServiceImpl extends ServiceImpl implements BuyerService {
    private static final Logger logger = LogManager.getLogger(BuyerServiceImpl.class);

    /**
     * Finds all buyers
     *
     * @return list of buyers
     * @throws ServicePersistentException if buyers are empty
     */
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

    /**
     * Finds random buyer by order amount
     *
     * @param orderAmount - number of orders
     * @return random Buyer
     * @throws ServicePersistentException if buyer does not exist
     */
    @Override
    public Buyer find(Integer orderAmount) throws ServicePersistentException {
        List<Buyer> buyers = findAll();
        List<Buyer> collect = buyers.stream()
                .filter(buyer -> buyer.getOrders().size() == orderAmount)
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            return collect.get(new Random().nextInt(collect.size()));
        } else {
            throw new ServicePersistentException("No such buyer");
        }
    }

    /**
     * Reads all buyers with specified offset and number of records
     *
     * @param offset
     * @param noOfRecords
     * @return Map<Integer, List < Buyer>>,where Integer represents number of found rows
     * @throws ServicePersistentException if finding error occurs
     */
    @Override
    public Map<Integer, List<Buyer>> find(int offset, int noOfRecords) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class, true);
            Map<Integer, List<Buyer>> map = dao.read(offset, noOfRecords);
            for (Map.Entry<Integer, List<Buyer>> entry : map.entrySet()) {
                buildList(entry.getValue());
            }
            return map;
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Deletes buyer by identity
     *
     * @param identity - buyer identity
     * @throws ServicePersistentException if deletion error occurs
     */
    @Override
    public void delete(Integer identity) throws ServicePersistentException {
        BuyerDAO dao;
        try {
            dao = transaction.createDao(BuyerDAO.class, false);
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
     * Saves buyer
     *
     * @param buyer - buyer to save
     * @throws ServicePersistentException if saving exception occurs
     */
    @Override
    public void save(Buyer buyer) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class, false);
            buyer.setId(dao.create(buyer));
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
     * Updates buyer
     *
     * @param buyer - buyer to update
     * @throws ServicePersistentException if updating error occurs
     */
    @Override
    public void update(Buyer buyer) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class, false);
            dao.update(buyer);
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
     * Finds buyer by identity
     *
     * @param identity - buyers' identity
     * @return - buyer corresponding to identity
     * @throws ServicePersistentException if buyer corresponding to identity does not exist
     */
    @Override
    public Buyer findById(Integer identity) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class, true);
            Optional<Buyer> optionalUser = dao.read(identity);
            if (optionalUser.isPresent()) {
                Buyer buyer = optionalUser.get();
                buildList(Arrays.asList(buyer));
                return buyer;
            }
            throw new ServicePersistentException("No such buyer");
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }

    /**
     * Fills buyers with corresponding fields
     *
     * @param buyers - buyers to fill with data
     * @throws ServicePersistentException if filling error occurs
     */
    private void buildList(List<Buyer> buyers) throws PersistentException {
        AddressDAO addressDAO = transaction.createDao(AddressDAO.class, true);
        OrderDAO orderDAO = transaction.createDao(OrderDAO.class, true);
        OrderItemDAO orderItemDAO = transaction.createDao(OrderItemDAO.class, true);
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
