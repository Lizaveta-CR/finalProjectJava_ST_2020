package by.tsvirko.music_shop.domain;

import lombok.*;

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
