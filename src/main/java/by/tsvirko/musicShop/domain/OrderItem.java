package by.tsvirko.musicShop.domain;

/**
 * Order item class: embedded class of Order and Product
 */
public class OrderItem extends Entity {
    private Integer product_id;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }
}
