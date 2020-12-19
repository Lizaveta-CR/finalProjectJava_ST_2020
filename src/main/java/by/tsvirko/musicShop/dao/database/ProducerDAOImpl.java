package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.OrderItemDAO;
import by.tsvirko.musicShop.dao.ProducerDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.OrderItem;
import by.tsvirko.musicShop.domain.Producer;
import by.tsvirko.musicShop.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProducerDAOImpl extends BaseDAO implements ProducerDAO {
    @Override
    public Integer create(Producer entity) throws PersistentException {
        return null;
    }

    @Override
    public Producer read(Integer identity) throws PersistentException {
        return null;
    }

    @Override
    public void update(Producer entity) throws PersistentException {

    }

    @Override
    public void delete(Integer identity) throws PersistentException {

    }

    @Override
    public List<Producer> read() throws PersistentException {
        return null;
    }

    public static void main(String[] args) throws PersistentException, ConnectionPoolException {

        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl(false);
        Transaction transaction = factory.createTransaction();
        ProducerDAO dao = transaction.createDao(ProducerDAO.class);
//        dao.read().forEach(System.out::println);
//        dao.delete(2);
//        dao.update(order);
//        Integer integer = dao.create(order);
        transaction.commit();
//        System.out.println(integer);
    }
}
