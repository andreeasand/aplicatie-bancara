package com.company;

import java.sql.*;

public class MySqlServer {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost;DatabaseName=DB;";
        String uid = "user";
        String pw = "pass";
        try {    // Load driver class
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException: " + e);
        }
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
