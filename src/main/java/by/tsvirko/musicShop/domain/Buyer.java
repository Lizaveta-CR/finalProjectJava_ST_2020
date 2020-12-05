package by.tsvirko.musicShop.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Buyer extends Person {
    private Long telephone;
    private BigDecimal balance;
    private Address address;
    private BigDecimal bonus = new BigDecimal(0.00);
    private List<Order> orders = new ArrayList<>(0);

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
