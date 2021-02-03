package music_shop.service.setupTestData;

import by.tsvirko.music_shop.dal.connection.ConnectionPool;
import by.tsvirko.music_shop.dal.exception.ConnectionPoolException;
import org.testng.annotations.AfterSuite;

import java.util.ResourceBundle;

public class AfterServicesSetDown {
    @AfterSuite
    public void setUpAfterSuite() {
        ConnectionPool.getInstance().destroy();
    }
}
