package by.tsvirko.music_shop.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Entity class.
 *
 * @see java.io.Serializable  Tomcat supports
 * persisting session state across all server restarts for session
 * objects that can be serialized.
 * For example, the server restarted - all session objects were saved
 * to the disk and were read from the disk upon reboot and the server
 * continued to work with those objects that were before the reboot.
 */
@EqualsAndHashCode
@ToString
@Getter
@Setter
public abstract class Entity<T> implements Serializable {
    private T id;
}
