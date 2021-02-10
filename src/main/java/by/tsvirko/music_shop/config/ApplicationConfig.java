package by.tsvirko.music_shop.config;

import by.tsvirko.music_shop.config.exception.ApplicationConfigException;
import by.tsvirko.music_shop.controller.constant.ParameterConstant;
import by.tsvirko.music_shop.controller.constant.ResourceBundleConstant;
import by.tsvirko.music_shop.dal.connection.ConnectionPool;
import by.tsvirko.music_shop.dal.exception.ConnectionPoolException;

import java.util.ResourceBundle;

/**
 * Application configuration class
 * <p>Application configuration class was made using static
 * * holder singleton pattern(Static factory, Lazy initialization,
 * Thread safe) The JVM defers initializing the Holder class until it is actually used,
 * and because the ApplicationConfig is initialized with a static initializer,
 * no additional synchronization is needed. The first call to getInstance
 * by any thread causes InstanceHolder to be loaded and initialized, at which
 * time the initialization of the Singleton happens through the static initializer. </>
 */
public class ApplicationConfig {
    private ApplicationConfig() {
    }

    private static class Holder {
        private static final ApplicationConfig INSTANCE = new ApplicationConfig();
    }

    public static ApplicationConfig getInstance() {
        return Holder.INSTANCE;
    }

    public void initApplication() throws ApplicationConfigException {
        ResourceBundle resource = ResourceBundle.getBundle(ParameterConstant.DATASOURCE_NAME.value());
        String driver = resource.getString(ResourceBundleConstant.DRIVER.value());
        String url = resource.getString(ResourceBundleConstant.URL.value());
        String user = resource.getString(ResourceBundleConstant.USER.value());
        String password = resource.getString(ResourceBundleConstant.PASSWORD.value());
        int poolSize = Integer.parseInt(resource.getString(ResourceBundleConstant.POOL_SIZE.value()));
        int maxSize = Integer.parseInt(resource.getString(ResourceBundleConstant.POOL_MAX_SIZE.value()));
        int checkConnectionTimeout = Integer.parseInt(resource.getString(ResourceBundleConstant.CONNECTIONS_TIMEOUT.value()));
        try {
            ConnectionPool.getInstance().initPoolData(driver, url, user, password, poolSize, maxSize, checkConnectionTimeout);
        } catch (ConnectionPoolException e) {
            throw new ApplicationConfigException("It is impossible to initialize application", e);
        }
    }

    public void destroyApplication() {
        ConnectionPool.getInstance().destroy();
    }
}
