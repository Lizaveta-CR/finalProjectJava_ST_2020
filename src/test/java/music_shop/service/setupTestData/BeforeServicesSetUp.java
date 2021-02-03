package music_shop.service.setupTestData;

import by.tsvirko.music_shop.dal.connection.ConnectionPool;
import by.tsvirko.music_shop.dal.exception.ConnectionPoolException;
import org.testng.annotations.BeforeSuite;

import java.util.ResourceBundle;

public class BeforeServicesSetUp {
    private final String DATASOURCE_NAME = "testdatabase";

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
}
