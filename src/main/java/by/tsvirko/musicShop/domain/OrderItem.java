package by.tsvirko.musicShop.domain;

/**
 * Order item class: embedded class of Order and Product
 */
//это чек
public class OrderItem extends Entity {
    private Integer product_id;
    private Integer amount;
    //TODO:date?

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
