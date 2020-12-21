package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.BuyerDAO;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.domain.Buyer;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;

import java.util.List;

public class BuyerServiceImpl extends ServiceImpl implements BuyerService {
    @Override
    public List<Buyer> findAll() throws ServicePersistentException {
        return null;
    }

    @Override
    public void delete(Integer identity) throws ServicePersistentException {

    }

    @Override
    public void save(Buyer buyer) throws ServicePersistentException {
        try {
            BuyerDAO dao = transaction.createDao(BuyerDAO.class);
            if (buyer.getId() != null) {
                dao.update(buyer);
            } else {
                buyer.setId(dao.create(buyer));
            }
        } catch (PersistentException e) {
            throw new ServicePersistentException(e);
        }
    }
}
