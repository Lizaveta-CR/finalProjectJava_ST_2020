package by.tsvirko.music_shop.constant;

public enum ParameterConstant {
    PRODUCT_ID("productId"),
    AMOUNT("amount");
    private final String name;

    private ParameterConstant(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
