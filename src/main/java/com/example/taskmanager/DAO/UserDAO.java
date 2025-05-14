package com.example.taskmanager.DAO;

import com.example.taskmanager.exceptions.InvalidInputException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    // Add new user to the database
    public void insertUser(User user) throws InvalidInputException, SQLException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new InvalidInputException("Cannot accept empty field");
        }

        String sql = "INSERT INTO users VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getEmail());

            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("The User ID already exists.");
        } catch (SQLException e) {
            System.err.println("Failed inserting user details");
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Get all Users in an Arraylist
    public List<User> getAllUser() throws SQLException {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("email")
                );

                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("There was a problem getting user details: \n" + e.getMessage());
        }
        return allUsers;
    }

    // Update User information in the database
    public void updateUserDetails(int id, String username, String email) {
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, email);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User details successfully updated");
            } else {
                System.out.println("No user with ID found");
            }
        } catch(SQLException e) {
            System.err.println("Failed to update user: \n" + e.getMessage());
        }
    }

    // Delete a user from the database
    public void deleteUser(int userID) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, userID);
            statement.executeUpdate();
            System.out.println("Successfully deleted task");
        } catch (SQLException e) {
            System.out.println("There was a problem deleting the user: \n" + e.getMessage());
        }
    }
}
