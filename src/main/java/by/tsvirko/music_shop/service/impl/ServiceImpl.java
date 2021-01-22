package by.tsvirko.music_shop.service.impl;

import by.tsvirko.music_shop.dao.Transaction;
import by.tsvirko.music_shop.service.Service;

/**
 * Service implementation. Stores transaction
 */
public abstract class ServiceImpl implements Service {
    protected Transaction transaction = null;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
