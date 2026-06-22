package com.fashionstore.controller;

import com.fashionstore.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet("/debug")
public class DebugServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Database Connection Debugger</h2>");
        
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");
        
        out.println("<p><b>DB_URL:</b> " + (url != null ? url : "NULL (Not Set)") + "</p>");
        out.println("<p><b>DB_USER:</b> " + (user != null ? user : "NULL (Not Set)") + "</p>");
        out.println("<p><b>DB_PASSWORD:</b> " + (pass != null ? "***SET***" : "NULL (Not Set)") + "</p>");
        
        out.println("<h3>Attempting DBConnection.getConnection()...</h3>");
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                out.println("<p style='color:green;'>SUCCESS: Connection established!</p>");
            } else {
                out.println("<p style='color:red;'>FAILED: Connection is null or closed.</p>");
            }
        } catch (Exception e) {
            out.println("<p style='color:red;'>EXCEPTION: " + e.getMessage() + "</p>");
        }
        
        out.println("<h3>Attempting Direct Connection...</h3>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String defaultUrl = url != null ? url : "jdbc:mysql://localhost:3306/fashion_store";
            String defaultUser = user != null ? user : "root";
            String defaultPass = pass != null ? pass : "Pinky@25";
            Connection directConn = DriverManager.getConnection(defaultUrl, defaultUser, defaultPass);
            if (directConn != null && !directConn.isClosed()) {
                out.println("<p style='color:green;'>SUCCESS: Direct Connection established!</p>");
            }
        } catch (Exception e) {
            out.println("<p style='color:red;'>FAILED: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
        
        out.println("</body></html>");
    }
}
