package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
@Setter
public class Country extends Entity<Integer> {
    private String name;
}
