package by.tsvirko.music_shop.constant;

public enum ParameterConstant {
    PRODUCT_ID("productId"),
    AMOUNT("amount"),
    BONUS("bonus"),
    ORDER("order"),
    ORDER_ID("orderId"),
    PRODUCTS("products"),
    MARK("mark"),
    PAGE("page"),
    BUYER_ID("buyerId"),
    ACCESS("access"),
    LOGIN("login");
    private final String name;

    private ParameterConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
