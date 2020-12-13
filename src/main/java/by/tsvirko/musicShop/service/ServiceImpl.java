package by.tsvirko.musicShop.service;

import by.tsvirko.musicShop.dao.Transaction;

public abstract class ServiceImpl implements Service {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
