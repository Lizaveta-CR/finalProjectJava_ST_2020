package by.tsvirko.musicShop.main;

import by.tsvirko.musicShop.domain.enums.Role;
import by.tsvirko.musicShop.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class Runner {
    private static String SELECT_ALL = "SELECT * FROM users";
    private static String INSERT_USERS = "INSERT INTO users ( login, password, role) VALUES ( ?, ?,?)";

    public static void main(String[] args) {
        Connection cn = null;
        ResourceBundle resource = ResourceBundle.getBundle("database");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String pass = resource.getString("db.password");
        try {
            cn = DriverManager.getConnection(url, user, pass);
//            Statement st = null;
            PreparedStatement st = null;
            try {
//                st = cn.createStatement();
                st = cn.prepareStatement(INSERT_USERS, Statement.RETURN_GENERATED_KEYS);


                ResultSet rs = null;
                try {
//                    rs = st.executeQuery(SELECT_ALL);
                    User hh = new User();
//                    hh.setId(5);
                    hh.setLogin("test1");
                    hh.setPassword("test1Pass");
                    hh.setRole(Role.BUYER);
//                    st.setInt(1, hh.getId());
                    st.setString(1, hh.getLogin());
                    st.setString(2, hh.getPassword());
                    st.setInt(3, hh.getRole().getIdentity());
                    st.executeUpdate();

                    rs = st.getGeneratedKeys();

                    if (rs.next()) {
                        int anInt = rs.getInt(1);
                        System.out.println(anInt);
                    }
//                    List<User> users = new ArrayList<>();
//                    while (rs.next()) {
//                        User us = new User();
////                        us.setName(rs.getString("name"));
//                        us.setId(Integer.parseInt(rs.getString("user_id")));
//                        us.setLogin(rs.getString("login"));
//                        us.setPassword(rs.getString("password"));
//                        us.setRole(Role.getByIdentity(rs.getInt("role")));
//                        users.add(us);
//                    }
//                    if (users.size() > 0) {
//                        System.out.println(users);
//                    }
                } catch (SQLException e) {
                    if (rs != null) {
                        rs.close();
                    }
                    System.err.println(e);
                }

            } catch (SQLException e) {
                if (st != null) {
                    st.close();
                }
                System.err.println(e);
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
