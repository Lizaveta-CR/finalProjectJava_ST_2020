package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Product class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class ProducerItem extends Entity<Integer> {
    private Product product;
    private Producer producer;
}
