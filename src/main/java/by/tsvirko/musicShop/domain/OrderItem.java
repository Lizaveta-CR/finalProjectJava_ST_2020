package by.tsvirko.musicShop.domain;

/**
 * Order item class: embedded class of Order and Product
 */
//TODO: возможно, что этот класс не нужен будет, а только для таблицы
public class OrderItem extends Entity {
    private Integer product_id;
    private Integer amount;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
