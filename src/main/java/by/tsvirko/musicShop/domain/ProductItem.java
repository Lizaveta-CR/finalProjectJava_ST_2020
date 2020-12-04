package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductItem extends Entity {
    private String type;
    private BigDecimal price;
    private Producer producer;

    public ProductItem() {
        producer = new Producer();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Producer getProducer() {
        return producer;
    }
}
