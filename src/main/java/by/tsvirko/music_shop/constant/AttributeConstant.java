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
    MENU("menu"),
    ORDER("order"),
    ORDERS("orders"),
    NO_OF_PAGES("noOfPages"),
    CURRENT_PAGE("currentPage"),
    ORDER_ITEM("orderItem"),
    RATEMAP("rateMap"),
    TOTAL("total"),
    USERS("users"),
    PRODUCT("product"),
    NOT_AVAILABLE_PRODUCTS("products"),
    BUYER("buyer"),
    BUYERS("buyers"),
    PERSONAL("personal"),
    PRODUCERS("producers"),
    PRODUCER("producer"),
    ERROR("error");
    private final String name;

    private AttributeConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }

}
