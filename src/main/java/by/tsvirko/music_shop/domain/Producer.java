package by.tsvirko.music_shop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * Producer class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Producer extends Entity<Integer> {
    private String name;
    private Country country;
    private Set<Product> products = new HashSet<>();
}
