package com.fashionstore.util;

import java.sql.Connection;

public class TestDB {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            if (con != null) {
                System.out.println("Database Connection Successful");
            } else {
                System.out.println("Database Connection Failed");
            }

        } catch (Exception e) {
            System.out.println("Database Exception occurred while connecting to DB");
            e.printStackTrace();
        }
    }
}
