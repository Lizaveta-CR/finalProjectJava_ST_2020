package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * ProductItem class
 */
public class ProductItem extends Entity {
    private String name;
    private BigDecimal price;
    private Set<Producer> producers = new HashSet<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set<Producer> getProducers() {
        return producers;
    }

    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }
}
