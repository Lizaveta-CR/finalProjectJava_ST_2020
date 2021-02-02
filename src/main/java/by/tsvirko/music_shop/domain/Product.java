package by.tsvirko.music_shop.domain;

import lombok.*;

import java.math.BigDecimal;

/**
 * ProductItem class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Product extends Entity<Integer> {
    private Category category;
    private Producer producer;
    private String model;
    private Boolean available;
    private String description;
    private String imageUrl;
    private BigDecimal price;
}
