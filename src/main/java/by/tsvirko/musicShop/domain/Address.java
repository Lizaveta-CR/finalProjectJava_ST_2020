package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Buyer's addresses class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Address extends Entity<Integer> {
    private String country;
    private String city;
    private int zipCode;
    private String street;
    private int apartment_number;
    private int house_number;
}
