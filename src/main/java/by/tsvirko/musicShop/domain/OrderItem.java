package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;

/**
 * Order item class: embedded class of Order and Product
 */
//это чек
public class OrderItem extends Entity {
    private Product product;
    private BigDecimal price;
    private Integer amount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
