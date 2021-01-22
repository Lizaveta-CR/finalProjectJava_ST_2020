package by.tsvirko.music_shop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Address class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Address extends Entity<Integer> {
    private Country country;
    private String city;
    private int zipCode;
    private String street;
    private int apartmentNumber;
    private int houseNumber;
}
