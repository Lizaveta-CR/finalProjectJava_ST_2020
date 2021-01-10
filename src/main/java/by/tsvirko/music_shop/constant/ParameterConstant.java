package by.tsvirko.music_shop.constant;

public enum ParameterConstant {
    PRODUCT_ID("productId"),
    AMOUNT("amount"),
    BONUS("bonus"),
    ORDER("order"),
    ORDER_ID("orderId"),
    PRODUCTS("products"),
    MARK("mark"),
    PAGE("page");
    private final String name;

    private ParameterConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
