package by.tsvirko.music_shop.controller.command.constant;

/**
 * Enum for resource bundle attributes
 */
public enum ResourceBundleConstant {
    RESOURCE("i18n.messages"),
    LANGUAGE("lang"),
    DRIVER("db.driver"),
    URL("db.url"),
    USER("db.user"),
    PASSWORD("db.password"),
    POOL_SIZE("db.poolsize"),
    POOL_MAX_SIZE("db.poolMaxSize"),
    CONNECTIONS_TIMEOUT("db.poolCheckConnectionTimeOut");

    private final String name;

    private ResourceBundleConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
