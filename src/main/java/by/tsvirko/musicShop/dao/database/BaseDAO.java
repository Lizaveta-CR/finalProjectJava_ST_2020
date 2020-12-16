package by.tsvirko.musicShop.dao.database;

import java.sql.Connection;

public abstract class BaseDAO {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
