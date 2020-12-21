package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * Person class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
abstract public class Person extends Entity {
    private String email;
    private String password;
}
