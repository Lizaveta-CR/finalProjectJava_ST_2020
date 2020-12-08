package by.tsvirko.musicShop.dao.pool;

import by.tsvirko.musicShop.dao.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final ReentrantLock lock = new ReentrantLock();
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private int maxSize;
    private int checkConnectionTimeout;
    private BlockingQueue<PooledConnection> freeConnections = new LinkedBlockingQueue<>();
    private Set<PooledConnection> usedConnections = new ConcurrentSkipListSet<>();

    private ConnectionPool() {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        this.url = resource.getString("db.url");
        this.user = resource.getString("db.user");
        this.password = resource.getString("db.password");
        this.poolSize = Integer.parseInt(resource.getString("db.poolSize"));
        this.maxSize = Integer.parseInt(resource.getString("db.poolMaxSize"));
        this.checkConnectionTimeout = Integer.parseInt(resource.getString("db.poolCheckConnectionTimeOut"));
    }

    /**
     * Initializes connection with url,user,password. Creates PooledConnection
     *
     * @throws ConnectionPoolException if initializing error occurs
     */
    public void initPoolData() throws ConnectionPoolException {
        try {
            destroy();
//            Class.forName(driverClass);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                PooledConnection pooledConnection = new PooledConnection(connection);
                freeConnections.put(pooledConnection);
            }
        } catch (SQLException | InterruptedException e) {
            logger.fatal("Error initializing connection pool", e);
            throw new ConnectionPoolException();
        }
    }

    /**
     * Gets connection
     *
     * @return connection
     * @throws ConnectionPoolException if limit of number of database connections is exceeded or
     *                                 it is impossible to connect to a database
     */
    public Connection getConnection() throws ConnectionPoolException {
        PooledConnection connection = null;
        while (connection == null) {
            try {
                lock.lock();
                try {
                    if (!freeConnections.isEmpty()) {
                        connection = freeConnections.take();
                        if (!connection.isValid(checkConnectionTimeout)) {
                            try {
                                connection.getConnection().close();
                            } catch (SQLException e) {
                            }
                            connection = null;
                        }
                    } else if (usedConnections.size() < maxSize) {
                        connection = createConnection();
                    } else {
                        logger.error("The limit of number of database connections is exceeded");
                        throw new ConnectionPoolException();
                    }
                } catch (InterruptedException | SQLException e) {
                    logger.error("It is impossible to connect to a database", e);
                    throw new ConnectionPoolException(e);
                }
            } finally {
                lock.unlock();
            }
        }
        usedConnections.add(connection);
        logger.debug(String.format("Connection was received from pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
        return connection;
    }

    /**
     * Creates connection
     *
     * @return PooledConnection
     * @throws SQLException
     */
    private PooledConnection createConnection() throws SQLException {
        return new PooledConnection(DriverManager.getConnection(url, user, password));
    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    /**
     * Releases connection and returnees it to pool
     *
     * @param connection
     */
    void freeConnection(PooledConnection connection) {
        try {
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.debug(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool", e1);
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
            }
        }
    }

    /**
     * Destroys connections
     */
    public void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (PooledConnection connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
            }
        }
        usedConnections.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }
}