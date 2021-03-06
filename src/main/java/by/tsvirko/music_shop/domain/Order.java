package by.tsvirko.music_shop.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Order class
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Order extends Entity<Integer> {
    private Buyer buyer;
    private BigDecimal price;
    private Date date;
    private Set<Product> productIts = new HashSet<>();

    public void addProduct(Product product) {
        productIts.add(product);
    }

    public void removeProduct(Product product) {
        productIts.remove(product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", date=" + date +
                ", productIts=" + productIts +
                '}';
    }
}
