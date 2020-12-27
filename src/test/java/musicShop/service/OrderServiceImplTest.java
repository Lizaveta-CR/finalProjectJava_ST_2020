//package musicShop.service;
//
//import by.tsvirko.musicShop.dao.database.TransactionFactoryImpl;
//import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
//import by.tsvirko.musicShop.dao.exception.PersistentException;
//import by.tsvirko.musicShop.dao.pool.ConnectionPool;
//import by.tsvirko.musicShop.service.OrderService;
//import by.tsvirko.musicShop.service.ProductService;
//import by.tsvirko.musicShop.service.ServiceFactory;
//import by.tsvirko.musicShop.service.exception.ServicePersistentException;
//import by.tsvirko.musicShop.service.impl.ServiceFactoryImpl;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Test;
//
//import java.util.ResourceBundle;
//
//public class OrderServiceImplTest {
//    private final String DATASOURCE_NAME = "database";
//    private OrderService orderService;
//
//    @BeforeSuite
//    public void setUpBeforeSuite() throws ConnectionPoolException {
//        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
//        String url = resource.getString("db.url");
//        String user = resource.getString("db.user");
//        String password = resource.getString("db.password");
//        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
//        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
//        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
//        ConnectionPool.getInstance().initPoolData(url, user, password, poolSize, maxSize, checkConnectionTimeout);
//    }
//
//    @BeforeClass
//    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
//        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
//        orderService = serviceFactory.getService(OrderService.class);
//    }
//
//    @Test
//    public void findAllTest() throws ServicePersistentException {
////        Assert.assertNotNull(orderService.findAll());
//        System.out.println(orderService.findAll());
//    }
//}
