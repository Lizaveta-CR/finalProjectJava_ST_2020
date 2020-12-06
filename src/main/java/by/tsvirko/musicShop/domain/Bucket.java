package by.tsvirko.musicShop.domain;

public class Bucket extends Entity {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
