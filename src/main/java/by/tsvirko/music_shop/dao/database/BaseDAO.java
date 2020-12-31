package by.tsvirko.music_shop.dao.database;

import java.sql.Connection;

public abstract class BaseDAO {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
