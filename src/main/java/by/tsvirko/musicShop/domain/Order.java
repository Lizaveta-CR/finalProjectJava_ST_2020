package by.tsvirko.musicShop.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Order class
 */
public class Order extends Entity{
    private Date date;
    private Buyer buyer;
    private Set<Product> products = new HashSet<>();


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
