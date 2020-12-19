package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Product class
 */
public class ProducerItem extends Entity {
    private Product product;
    private Producer producer;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "ProducerItem{" +
                "product=" + product +
                ", producer=" + producer +
                '}';
    }
}
