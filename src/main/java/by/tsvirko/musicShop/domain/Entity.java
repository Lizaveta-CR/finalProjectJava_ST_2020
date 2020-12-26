package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
abstract public class Entity<T> implements Serializable {
    private T id;
}
