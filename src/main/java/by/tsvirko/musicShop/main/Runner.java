package by.tsvirko.musicShop.main;

import by.tsvirko.musicShop.domain.Role;
import by.tsvirko.musicShop.domain.User;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Runner {
    private static String SELECT_ALL = "SELECT * FROM users";

    public static void main(String[] args) {
        Connection cn = null;
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        try {
            cn = DriverManager.getConnection(url, user, pass);
            Statement st = null;
            try {
                st = cn.createStatement();

                ResultSet rs = null;
                try {
                    rs = st.executeQuery(SELECT_ALL);

                    List<User> users = new ArrayList<>();
                    while (rs.next()) {
                        User us = new User();
                        us.setId(rs.getInt("user_id"));
                        us.setLogin(rs.getString("login"));
                        us.setPassword(rs.getString("password"));
                        us.setRole(Role.getByIdentity(rs.getInt("role")));
                        users.add(us);
                    }
                    if (users.size() > 0) {
                        System.out.println(users);
                    }
                } catch (SQLException e) {
                    if (rs != null) {
                        rs.close();
                    }
                }

            } catch (SQLException e) {
                if (st != null) {
                    st.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
