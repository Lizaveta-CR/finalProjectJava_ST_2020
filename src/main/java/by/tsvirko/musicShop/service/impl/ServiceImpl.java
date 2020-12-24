package by.tsvirko.musicShop.service.impl;

import by.tsvirko.musicShop.dao.Transaction;
import by.tsvirko.musicShop.service.Service;

public abstract class ServiceImpl implements Service {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
