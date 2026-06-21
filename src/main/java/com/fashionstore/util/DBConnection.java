package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";     
    private static final String PASSWORD = "Pinky@25";     

    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                // Load MySQL Driver (optional for newer versions but safe)
                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fashion_store",
                	    "root",
                	    "Pinky@25");
                System.out.println("✅ Database Connected Successfully");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL Driver not found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to database");
            e.printStackTrace();
        }

        return connection;
    }
}
