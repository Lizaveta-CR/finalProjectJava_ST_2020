package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.OrderItemDAO;
import by.tsvirko.musicShop.dao.ProducerItemDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.ProducerItem;
import by.tsvirko.musicShop.service.ProducerItemService;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

public class ProducerItemServiceImpl extends ServiceImpl implements ProducerItemService {
    @Override
    public void save(ProducerItem item) throws ServicePersistentException {
        try {
            ProducerItemDAO dao = transaction.createDao(ProducerItemDAO.class, false);
            dao.create(item);
            transaction.commit();
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
