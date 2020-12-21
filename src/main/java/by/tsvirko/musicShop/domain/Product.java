package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * ProductItem class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Product extends Entity {
    private String type;
    private Integer categoryNum;
    private String model;
    private Boolean available;
    private String description;
    private String image_url;
    private BigDecimal price;
}
