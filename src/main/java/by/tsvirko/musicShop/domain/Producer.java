package by.tsvirko.musicShop.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Producer class
 */
public class Producer extends Entity {
    private String name;
    private String country;
    private Set<ProductItem> productItems = new HashSet<>();


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

    public Set<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(Set<ProductItem> productItems) {
        this.productItems = productItems;
    }
}
