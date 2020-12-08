package by.tsvirko.musicShop.domain;

//TODO: а надо ли?
public class ProductRate extends Entity {
    private Buyer buyer;
    private Product product;
    private Integer mark;

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }
}
