package by.tsvirko.musicShop.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Producer class
 */
public class Producer extends Entity {
    private String name;
    private String country;
    private Set<Product> products = new HashSet<>();


    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
