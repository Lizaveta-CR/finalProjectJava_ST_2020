package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Person class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
abstract public class Person extends Entity<Integer>{
    private String email;
    private String password;
}
