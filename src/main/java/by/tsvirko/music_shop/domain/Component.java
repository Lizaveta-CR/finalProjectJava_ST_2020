package by.tsvirko.music_shop.domain;

/**
 * Sets the interface for all compound objects.
 *
 * @param <T>
 */
public interface Component<T> {
    void add(Component c);

    Object getChild(int i);

    void remove(Component c);

    int getSize();
}
