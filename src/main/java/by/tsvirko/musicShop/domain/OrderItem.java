package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;

/**
 * Order item class: embedded class of Order and Product
 */
//это чек
public class OrderItem extends Entity {
    private Product product;
    private BigDecimal price;
    private Byte amount;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Byte getAmount() {
        return amount;
    }

    public void setAmount(Byte amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
