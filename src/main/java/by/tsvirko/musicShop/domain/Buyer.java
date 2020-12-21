package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Buyer class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Buyer extends Person {
    private Long telephone;
    private BigDecimal balance;
    private Address address;
    private BigDecimal bonus = new BigDecimal(0.00);
    private Boolean enabled = true;
    private List<Order> orders = new ArrayList<>();
}
