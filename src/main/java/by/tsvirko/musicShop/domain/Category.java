package by.tsvirko.musicShop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class Category extends Entity<Integer> implements Component<Category> {
    private String name;
    private List<Component> components;
    private List<Product> products;

    public Category() {
        this.components = new ArrayList<>();
        this.products = new ArrayList<>();
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
    public int getSize() {
        return components.size();
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}
