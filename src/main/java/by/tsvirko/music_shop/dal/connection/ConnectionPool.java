package by.tsvirko.music_shop.dal.connection;

import by.tsvirko.music_shop.dal.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection pool class: so that every time you execute a request, you don't have to create a new connection.
 */
final public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final ReentrantLock lock = new ReentrantLock();
    private String url;
    private String user;
    private String password;
    private int maxSize;
    private int checkConnectionTimeout;
    /**
     * Available connections
     */
    private BlockingQueue<ConnectionProxy> freeConnections = new LinkedBlockingQueue<>();
    /**
     * Used connections (we work with this connections)
     */
    private Set<ConnectionProxy> usedConnections = new ConcurrentSkipListSet<>();

    /**
     * ConnectionPool constructor.
     */
    private ConnectionPool() {
    }

    /**
     * Initializes connection with url,user,password. Creates PooledConnection.
     *
     * @throws ConnectionPoolException if initializing error occurs
     */
    public void initPoolData(String driver, String url, String user, String password, int poolSize, int maxSize, int checkConnectionTimeout) throws ConnectionPoolException {
        try {
            destroy();
            Class.forName(driver);
            this.url = url;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.checkConnectionTimeout = checkConnectionTimeout;
            for (int i = 0; i < poolSize; i++) {
                freeConnections.put(createConnection());
            }
        } catch (SQLException | InterruptedException | ClassNotFoundException e) {
            throw new ConnectionPoolException("Fatal: error initializing connection pool", e);
        }
    }

    /**
     * Gets connection.
     *
     * @return obtained connection instance
     * @throws ConnectionPoolException if limit of number of database connections is exceeded or
     *                                 it is impossible to connect to a database
     */
    public Connection getConnection() throws ConnectionPoolException {
        ConnectionProxy connection = null;
        while (connection == null) {
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
                    throw new ConnectionPoolException("The limit of number of database connections is exceeded");
                }
            } catch (InterruptedException | SQLException e) {
                throw new ConnectionPoolException("It is impossible to connect to a database", e);
            }
        }
        usedConnections.add(connection);
        logger.debug(String.format("Connection was received from pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
        return connection;
    }

    /**
     * Creates connection.
     *
     * @return ConnectionProxy instance
     * @throws SQLException
     */
    private ConnectionProxy createConnection() throws SQLException {
        return new ConnectionProxy(DriverManager.getConnection(url, user, password));
    }

    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            lock.lock();
            try {
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
     * Releases connection and returnees it to pool.
     *
     * @param connection - proxy connection
     */
    void freeConnection(ConnectionProxy connection) {
        try {
            lock.lock();
            if (connection.isValid(checkConnectionTimeout)) {
                connection.clearWarnings();
                connection.setAutoCommit(true);
                usedConnections.remove(connection);
                freeConnections.put(connection);
                logger.info(String.format("Connection was returned into pool. Current pool size: %d used connections; %d free connection", usedConnections.size(), freeConnections.size()));
            }
        } catch (SQLException | InterruptedException e1) {
            logger.warn("It is impossible to return database connection into pool:", e1.getMessage());
            try {
                connection.getConnection().close();
            } catch (SQLException e2) {
                logger.warn("It is impossible to close connection:", e2.getMessage());
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Destroys connections.
     */
    public void destroy() {
        usedConnections.addAll(freeConnections);
        freeConnections.clear();
        for (ConnectionProxy connection : usedConnections) {
            try {
                connection.getConnection().close();
            } catch (SQLException e) {
                logger.error("Impossible to close connection. Database access error", e);
            }
        }
        usedConnections.clear();
        logger.info("Connections were destroyed. Used connections size = " + usedConnections.size());
    }

    @Override
    protected void finalize() throws Throwable {
        destroy();
    }

    /**
     * Provides count of available connections which can be picked from this pool now.
     *
     * @return available connection count
     */
    public int getAvailableConnectionsCount() {
        return freeConnections.size();
    }
}
