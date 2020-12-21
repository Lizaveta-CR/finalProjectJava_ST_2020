package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Producer class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Producer extends Entity {
    private String name;
    private String country;
    private Set<Product> products = new HashSet<>();
}
