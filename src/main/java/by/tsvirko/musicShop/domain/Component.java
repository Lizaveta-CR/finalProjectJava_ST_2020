package by.tsvirko.musicShop.domain;

/**
 *Sets the interface for all compound objects.
 *
 * @param <T>
 */
public interface Component<T> {
    void add(Component c);

    Object getChild(int i);

    void remove(Component c);

//    T collect();

    int getSize();
}
