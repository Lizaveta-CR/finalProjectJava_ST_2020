package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.ProducerItemDAO;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.domain.ProducerItem;
import by.tsvirko.music_shop.service.ProducerItemService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;

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
