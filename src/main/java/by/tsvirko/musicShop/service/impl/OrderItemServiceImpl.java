package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.OrderItemDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.OrderItem;
import by.tsvirko.musicShop.service.OrderItemService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

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
            throw new ServicePersistentException(e);
        }
    }
}
