package musicShop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.dao.pool.ConnectionPool;
import by.tsvirko.music_shop.service.AddressService;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class AddressServiceImplTest {
    private final String DATASOURCE_NAME = "database";
    private AddressService addressService;

    @BeforeSuite
    public void setUpBeforeSuite() throws ConnectionPoolException {
        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
        String url = resource.getString("db.url");
        String driver = resource.getString("db.driver");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        ConnectionPool.getInstance().initPoolData(driver,url, user, password, poolSize, maxSize, checkConnectionTimeout);
    }

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        addressService = serviceFactory.getService(AddressService.class);
    }

    @Test
    public void findAllTest() throws ServicePersistentException {
//        Assert.assertNotNull(orderService.findAll());
        System.out.println(addressService.findAll());
    }
}
