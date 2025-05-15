package com.example.taskmanager.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDb {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connection Success");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
