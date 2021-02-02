package by.tsvirko.music_shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Person class
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public abstract class Person extends Entity<Integer> {
    private String email;
    private String password;
}
