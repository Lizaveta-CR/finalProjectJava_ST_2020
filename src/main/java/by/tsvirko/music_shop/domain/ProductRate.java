package by.tsvirko.music_shop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Product Rate class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class ProductRate extends Entity <Integer>{
    private Buyer buyer;
    private Product product;
    private byte mark;
}
