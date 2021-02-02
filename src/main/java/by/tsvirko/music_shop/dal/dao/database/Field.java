package by.tsvirko.music_shop.dal.dao.database;

/**
 * Enum for storing database fields names
 */
public enum Field {
    ID("id"),
    BUYER_ID("buyer_id"),
    COUNTRY_ID("country_id"),
    CITY("city"),
    ZIP_CODE("zip_code"),
    STREET("street"),
    APARTMENT_NUMBER("apartment_number"),
    HOUSE_NUMBER("house_number"),
    EMAIL("email"),
    TELEPHONE("telephone"),
    BALANCE("balance"),
    BONUS("bonus"),
    ENABLED("enabled"),
    DATE("date"),
    PRICE("price"),
    PRODUCT_ID("product_id"),
    AMOUNT("amount"),
    NAME("name"),
    CATEGORY_ID("category_id"),
    MODEL("model"),
    AVAILABLE("available"),
    DESCRIPTION("description"),
    IMG("img"),
    LOGIN("login"),
    SURNAME("surname"),
    PASSWORD("password"),
    ROLE("role"),
    MARK("mark"),
    AVG_MARK("avg_mark"),
    PARENT_ID("parent_id"),
    PRODUCER_ID("producer_id");

    private final String value;

    Field(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
