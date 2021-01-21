package by.tsvirko.music_shop.constant;

public enum ParameterConstant {
    PRODUCT_ID("productId"),
    EMPLOYEE_ID("employeeId"),
    AMOUNT("amount"),
    BONUS("bonus"),
    ORDER("order"),
    ORDER_ID("orderId"),
    PRODUCTS("products"),
    MARK("mark"),
    PAGE("page"),
    BUYER_ID("buyerId"),
    ACCESS("access"),
    LOGIN("login"),
    TO("to"),
    SUBJECT("subject"),
    BODY("body"),
    PRODUCER_ID("producerId"),
    MAIL_PROP("mail.properties");

    private final String name;

    private ParameterConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
