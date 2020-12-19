package by.tsvirko.musicShop.dao.database;

import by.tsvirko.musicShop.dao.ProducerItemDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.Producer;
import by.tsvirko.musicShop.domain.ProducerItem;
import by.tsvirko.musicShop.domain.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProducerItemDAOImpl extends BaseDAO implements ProducerItemDAO {
    private static final Logger logger = LogManager.getLogger(ProducerItemDAOImpl.class);

    private static final String SQL_INSERT_PRODUCER_ITEM = "INSERT INTO producer_items (producer_id, product_id) VALUES (?, ?)";


    /**
     * Creates producerItem in database
     *
     * @param entity
     * @return null
     * @throws PersistentException if a database access error occurs
     */
    @Override
    public Integer create(ProducerItem entity) throws PersistentException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PRODUCER_ITEM);
            statement.setInt(1, entity.getProducer().getId());
            statement.setInt(2, entity.getProduct().getId());
            statement.executeUpdate();

            logger.debug("ProducerItem with id= " + entity.getProducer().getId() + ", " + entity.getProduct().getId() + " was created");
        } catch (SQLException e) {
            logger.error("It is impossible co connect to database");
            throw new PersistentException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException | NullPointerException e) {
                logger.error("Database access connection failed. Impossible to close statement");
            }
            return null;
        }
    }

    @Override
    public ProducerItem read(Integer identity) throws PersistentException {
        logger.error("Unable operation");
        throw new PersistentException("Unable to perform read() operation with ProducerItem");
    }

    @Override
    public void update(ProducerItem entity) throws PersistentException {
        logger.error("Unable operation");
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        logger.error("Unable operation");
    }

    public static void main(String[] args) throws PersistentException, ConnectionPoolException {
        Producer producer = new Producer();
        producer.setId(2);
        Product pr = new Product();
        pr.setId(1);
        ProducerItem pi = new ProducerItem();
        pi.setProducer(producer);
        pi.setProduct(pr);
        ConnectionPool.getInstance().initPoolData();
        TransactionFactory factory = new TransactionFactoryImpl(false);
        Transaction transaction = factory.createTransaction();
        ProducerItemDAO dao = transaction.createDao(ProducerItemDAO.class);
//        dao.read().forEach(System.out::println);
//        dao.delete(1);
//        dao.update(producer);
        Integer integer = dao.create(pi);
        transaction.commit();
//        System.out.println(integer);
    }
}
