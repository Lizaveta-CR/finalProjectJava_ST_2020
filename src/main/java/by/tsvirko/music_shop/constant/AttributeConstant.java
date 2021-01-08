package by.tsvirko.music_shop.constant;

public enum AttributeConstant {
    REDIRECTED_DATA("redirectedData"),
    COMMAND("command"),
    AUTHORIZED_USER("authorizedUser"),
    AUTHORIZED_BUYER("authorizedBuyer"),
    BUYER_INFO("buyerInfo"),
    SECURITY_FILTER_MESSAGE("securityFilterMessage"),
    MESSAGE("message"),
    COUNTRIES("countries"),
    ADDRESS("address"),
    CATEGORY("category"),
    MENU("menu");

    private final String name;

    private AttributeConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }

}
