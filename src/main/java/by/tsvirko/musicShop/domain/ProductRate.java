package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Product Rate class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class ProductRate extends Entity {
    private Buyer buyer;
    private Product product;
    private byte mark;
}
