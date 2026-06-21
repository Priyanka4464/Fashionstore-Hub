package com.fashionstore.dao.impl;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements UserDAO {

    private Connection con;

    public UserDAOImpl() {
        con = DBConnection.getConnection();
    }

    @Override
    public boolean registerUser(User user) {
        boolean status = false;
        try {
            String sql = "INSERT INTO users (full_name, email, password, phone, gender, address) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getGender());
            ps.setString(6, user.getAddress());

            int rows = ps.executeUpdate();
            status = rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public User loginUser(String email, String password) {
        User user = null;
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setPhone(rs.getString("phone"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setRole("customer"); // hardcoded since no role column
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    @Override
    public boolean isEmailExists(String email) {
        boolean exists = false;
        try {
            String sql = "SELECT user_id FROM users WHERE email = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            exists = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }
}
