package by.tsvirko.music_shop.constant;

/**
 * Enum for parameters
 */
public enum ParameterConstant {
    DATASOURCE_NAME("database"),
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
    MAIL_PROP("mail.properties"),
    FILE("file"),
    PASS("password"),
    COUNTRY("country"),
    CITY("city"),
    ZIP_CODE("zip_code"),
    STREET("street"),
    APARTMENT_NUMBER("apartmentNumber"),
    HOUSE_NUMBER("houseNumber"),
    EMAIL("email"),
    TELEPHONE("telephone"),
    BALANCE("balance"),
    MODEL("model"),
    CATEGORY("category"),
    PRODUCER("producer"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    IDENTITY("identity"),
    SURNAME("surname"),
    CONFIRM_PASS("confirm_password"),
    ROLE("role"),
    NEW_PASS("new_password"),
    COOKIE_LOCALE("cookieLocale");

    private final String name;

    private ParameterConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
