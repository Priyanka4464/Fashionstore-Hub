package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fashionstore.dao.AdminDAO;
import com.fashionstore.util.DBConnection;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean validateAdmin(String username, String password) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT * FROM admin WHERE username=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}