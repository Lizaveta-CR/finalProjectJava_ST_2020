package musicShop.dao.pool;

import by.tsvirko.musicShop.dao.pool.ConnectionPool;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionPoolTest {
//    private final String DATASOURCE_NAME = "testdatabase";
//    Connection connection;

//    @BeforeClass
//    public void setUpConnection() throws SQLException {
//        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
//        String url = resource.getString("db.url");
//        String user = resource.getString("db.user");
//        String password = resource.getString("db.password");
//        connection = DriverManager.getConnection(url, user, password);
//    }

//    @Test
//    public void returnExternalConnectionTest() throws SQLException {
//        ConnectionPool poolToTest = ConnectionPool.getInstance();
//        int expectedConnectionCount = poolToTest.getAvailableConnectionsCount();
//        System.out.println(expectedConnectionCount);
//        ResourceBundle resource = ResourceBundle.getBundle(DATASOURCE_NAME);
//        String url = resource.getString("db.url");
//        String user = resource.getString("db.user");
//        String password = resource.getString("db.password");
//        connection = DriverManager.getConnection(url, user, password);
//        System.out.println(poolToTest.getAvailableConnectionsCount());
//        Assert.assertEquals(expectedConnectionCount, poolToTest.getAvailableConnectionsCount());
//    }
}
