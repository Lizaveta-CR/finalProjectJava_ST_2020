package by.tsvirko.musicShop.domain;

import by.tsvirko.musicShop.domain.enums.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

/**
 * User class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class User extends Entity<Integer> {
    private String name;
    private String surname;
    private String login;
    private String password;
    private Role role;
}
