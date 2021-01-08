package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.OrderItemDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.OrderItem;
import by.tsvirko.music_shop.service.OrderItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

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
}
