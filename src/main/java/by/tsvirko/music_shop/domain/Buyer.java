package by.tsvirko.music_shop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Buyer class
 */
@EqualsAndHashCode(callSuper = true)
//@ToString
@Getter
@Setter
public class Buyer extends Person {
    private Long telephone;
    private BigDecimal balance;
    private Address address;
    private BigDecimal bonus = new BigDecimal(0.00);
    private Boolean enabled = true;
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "telephone=" + telephone +
                ", balance=" + balance +
                ", address=" + address +
                ", bonus=" + bonus +
                ", enabled=" + enabled +
                ", orders=" + orders +
                '}';
    }
}
