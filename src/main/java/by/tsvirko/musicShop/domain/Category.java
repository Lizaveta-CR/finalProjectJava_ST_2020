package by.tsvirko.musicShop.domain;

import java.util.ArrayList;
import java.util.List;

public class Category extends Entity implements Component<Category> {
    private String name;
    private List<Component> components;

    public Category() {
        this.components = new ArrayList<>();
    }

    @Override
    public void add(Component c) {
        components.add(c);
    }

    @Override
    public Object getChild(int i) {
        return components.get(i);
    }

    @Override
    public void remove(Component c) {
        components.remove(c);
    }

    @Override
    public Category collect() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }
}
