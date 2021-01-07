package by.tsvirko.music_shop.constant;

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
