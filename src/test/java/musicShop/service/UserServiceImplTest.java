package musicShop.service;

import by.tsvirko.musicShop.dao.database.TransactionFactoryImpl;
import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import by.tsvirko.musicShop.dao.exception.PersistentException;
import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import by.tsvirko.musicShop.domain.User;
import by.tsvirko.musicShop.domain.enums.Role;
import by.tsvirko.musicShop.service.ServiceFactory;
import by.tsvirko.musicShop.service.UserService;
import by.tsvirko.musicShop.service.exception.PasswordException;
import by.tsvirko.musicShop.service.exception.ServicePersistentException;
import by.tsvirko.musicShop.service.impl.ServiceFactoryImpl;
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
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        ConnectionPool.getInstance().initPoolData(url, user, password, poolSize, maxSize, checkConnectionTimeout);
    }

    @BeforeClass
    public void setUpBeforeClass() throws PersistentException, ServicePersistentException {
        ServiceFactory serviceFactory = new ServiceFactoryImpl(new TransactionFactoryImpl());
        userService = serviceFactory.getService(UserService.class);
    }

    @BeforeTest()
    public void createUser() throws ServicePersistentException {
        user = new User();
        user.setId(1);
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
}
