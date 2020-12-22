package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Order class
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Order extends Entity {
    private Buyer buyer;
    private BigDecimal price;
    private Date date;
    private Set<Product> productIts = new HashSet<>();

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", date=" + date +
                ", productIts=" + productIts +
                '}';
    }
}
