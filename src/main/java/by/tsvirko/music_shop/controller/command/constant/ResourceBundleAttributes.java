package by.tsvirko.music_shop.controller.command.constant;

/**
 * Enum for resource bundle attributes
 */
public enum ResourceBundleAttributes {
    RESOURCE("i18n.messages"),
    LANGUAGE("lang");

    private final String name;

    private ResourceBundleAttributes(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
