package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Order item class: embedded class of Order and Product
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class OrderItem extends Entity<Integer> {
    private Product product;
    private BigDecimal price;
    private Byte amount;
}
