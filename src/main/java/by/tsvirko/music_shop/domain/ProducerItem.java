package by.tsvirko.music_shop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
