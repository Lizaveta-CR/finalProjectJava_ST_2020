package musicShop.dao;

import by.tsvirko.musicShop.dao.CategoryDAO;
import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.dao.TransactionFactory;
import by.tsvirko.musicShop.dao.database.TransactionFactoryImpl;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class CategoryDAOTest {
    private final String DATASOURCE_NAME = "database";

    @BeforeClass
    public void setUpBeforeSuite() throws ConnectionPoolException {
        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        ConnectionPool.getInstance().initPoolData(url, user, password, poolSize, maxSize, checkConnectionTimeout);
    }

    @Test
    public void readTest() throws PersistentException {
        TransactionFactory factory = new TransactionFactoryImpl();
        Transaction transaction = factory.createTransaction();
        CategoryDAO dao = transaction.createDao(CategoryDAO.class, true);
//        System.out.println(dao.read());
    }
//        public static void main(String[] args) throws PersistentException, ConnectionPoolException {
////        Buyer user = new Buyer();
////        user.setId(2);
//////        user.setBonus(new BigDecimal(0));
////        user.setEmail("test555");
////        user.setTelephone(1234564321796L);
////        user.setBalance(new BigDecimal(1000));
////        user.setSurname("test");
////        user.setEnabled(Role.BUYER);
//        ConnectionPool.getInstance().initPoolData();
//        TransactionFactory factory = new TransactionFactoryImpl(false);
//        Transaction transaction = factory.createTransaction();
//        BuyerDAO dao = transaction.createDao(BuyerDAO.class);
//        dao.read();
//        transaction.commit();
////        System.out.println(integer);
//    }
}
