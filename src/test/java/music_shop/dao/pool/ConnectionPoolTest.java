package music_shop.dao.pool;

import by.tsvirko.music_shop.dao.exception.ConnectionPoolException;
import by.tsvirko.music_shop.dao.pool.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ResourceBundle;

public class ConnectionPoolTest {
    final String DATABASE_SOURCE = "testdatabase";
    ConnectionPool instance;

    @BeforeTest
    public void setUp() throws ConnectionPoolException {
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_SOURCE);
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String driver = resource.getString("db.driver");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        instance = ConnectionPool.getInstance();
        instance.initPoolData(driver, url, user, password, poolSize, maxSize, checkConnectionTimeout);
    }

    @Test
    public void initPoolDataException() {
        ResourceBundle resource = ResourceBundle.getBundle("testdatabaseException");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String driver = resource.getString("db.driver");
        String password = resource.getString("db.password");
        int poolSize = Integer.parseInt(resource.getString("db.poolsize"));
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        int checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
        Assert.assertThrows(ConnectionPoolException.class, () -> ConnectionPool.getInstance()
                .initPoolData(driver, url, user, password, poolSize, maxSize, checkConnectionTimeout));
    }

    @Test
    public void destroy() {
        instance.destroy();
        Assert.assertEquals(instance.getAvailableConnectionsCount(), 0);
    }

    @Test(expectedExceptions = ConnectionPoolException.class)
    public void availableConnectionsAmountGetConnectionMaxPoolSizeException() throws ConnectionPoolException {
        instance.destroy();
        ResourceBundle resource = ResourceBundle.getBundle(DATABASE_SOURCE);
        int maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        for (int i = 0; i < maxSize; i++) {
            instance.getConnection();
        }
    }
}
