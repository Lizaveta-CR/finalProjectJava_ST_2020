package music_shop.service;

import by.tsvirko.music_shop.dao.database.TransactionFactoryImpl;
import by.tsvirko.music_shop.dao.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dao.exception.PersistentException;
import by.tsvirko.music_shop.dao.pool.ConnectionPool;
import by.tsvirko.music_shop.domain.User;
import by.tsvirko.music_shop.domain.enums.Role;
import by.tsvirko.music_shop.service.ServiceFactory;
import by.tsvirko.music_shop.service.UserService;
import by.tsvirko.music_shop.service.exception.ServicePersistentException;
import by.tsvirko.music_shop.service.impl.ServiceFactoryImpl;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.ResourceBundle;

public class UserServiceImplTest {
    private final String DATASOURCE_NAME = "testdatabase";
    private UserService userService;
    private User user;

    @BeforeSuite
    public void setUpBeforeSuite() throws ConnectionPoolException {
        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String driver = resource.getString("db.driver");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        ConnectionPool.getInstance().initPoolData(driver, url, user, password, poolSize, maxSize, checkConnectionTimeout);
    }

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        userService = serviceFactory.getService(UserService.class);
    }

    @BeforeTest
    public void createUser() throws ServicePersistentException {
        user = new User();
//        user.setId(1);
        user.setLogin("manager1");
        user.setName("Alexey");
        user.setSurname("Tsar");
        user.setPassword("manager1");
        user.setRole(Role.MANAGER);
    }

    @Test
    public void findAll() throws ServicePersistentException {
        List<User> users = userService.findAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void delete() throws ServicePersistentException {
        userService.delete(user.getId());
        List<User> users = userService.findAll();
        Assert.assertFalse(users.contains(user));
    }

    @Test
    public void deleteException() throws ServicePersistentException {
        Assert.assertThrows(ServicePersistentException.class, () -> userService.delete(100));
    }

    @Test
    public void saveTest() throws ServicePersistentException {
        userService.save(user);
        List<User> users = userService.findAll();
        Assert.assertTrue(users.contains(user));
    }

    @DataProvider(name = "names")
    public Object[] createCorrecttData() {

        return new Object[]{
                "Kate"
        };
    }

    @Test(dataProvider = "names")
    public void updateTest(String name) throws ServicePersistentException {
        user.setName(name);
        userService.save(user);
        Assert.assertEquals(name, user.getName());
    }

    @Test()
    public void updateExceptionTest() {
        user.setName(null);
        Assert.assertThrows(ServicePersistentException.class, () -> userService.save(user));
    }
}
