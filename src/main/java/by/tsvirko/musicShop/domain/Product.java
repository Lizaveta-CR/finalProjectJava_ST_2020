package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;

/**
 * ProductItem class
 */
public class Product extends Entity {
    private String name;
    private BigDecimal price;
    private String image_url;

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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
