package musicShop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.dao.pool.ConnectionPool;
import by.tsvirko.music_shop.service.ProductService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class ProductServiceImplTest {
    private final String DATASOURCE_NAME = "database";
    private ProductService productService;

    @BeforeSuite
    public void setUpBeforeSuite() throws ConnectionPoolException {
        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        String driver = resource.getString("db.driver");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        ConnectionPool.getInstance().initPoolData(driver, url, user, password, poolSize, maxSize, checkConnectionTimeout);
    }

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        productService = serviceFactory.getService(ProductService.class);
    }

    @Test
    public void findAllTest() throws ServicePersistentException {
//        Assert.assertNotNull(productService.findAll());
//        System.out.println(productService.findAll());
//        productService.findByCategory();
    }
}
