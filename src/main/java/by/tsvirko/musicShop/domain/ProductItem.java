package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Product class
 */
public class ProductItem {
    private Product product;
    private BigDecimal price;
    private Set<Producer> producers = new HashSet<>();

    public ProductItem() {
        product = new Product();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }
}
