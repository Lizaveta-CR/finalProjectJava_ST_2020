package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Order class
 */
public class Order extends Entity {
    private Buyer buyer;
    private BigDecimal price;
    private Date date;
    private Set<Product> productIts = new HashSet<>();

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Set<Product> getProductIts() {
        return productIts;
    }

    public void setProductIts(Set<Product> productIts) {
        this.productIts = productIts;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "buyer=" + buyer +
                ", price=" + price +
                ", date=" + date +
                ", productIts=" + productIts +
                '}';
    }
}
