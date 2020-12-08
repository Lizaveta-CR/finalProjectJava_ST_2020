package by.tsvirko.musicShop.dao.database;

import java.sql.Connection;

public abstract class BaseDao {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
