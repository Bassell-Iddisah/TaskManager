package com.example.taskmanager.DAO;

import com.example.taskmanager.exceptions.InvalidInputException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.utils.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDAO {
    // Add new task to the database
    public void insertTask(Task task) throws InvalidInputException, SQLException {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new InvalidInputException("Cannot accept empty field");
        }

        String sql = "INSERT INTO tasks (title, description, status, priority, due_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getStatus());
            statement.setInt(4, task.getPriority());
            statement.setString(5, task.getDueDate());
            statement.setString(6, task.getCreated_at());
            statement.setString(7, task.getUpdated_at());


            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("The Task ID already exists.");
        } catch (SQLException e) {
            System.err.println("Failed inserting task details");
            System.err.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Get all Tasks in an Arraylist
    public List<Task> getAllTasks() throws SQLException {
        List<Task> allTasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
            Task task = new Task(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getString("status"),
                    resultSet.getInt("priority"),
                    resultSet.getString("due_date"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
            );

            allTasks.add(task);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("There was a problem getting task details: \n" + e.getMessage());
        }
        return allTasks;
    }

    // Update task information in the database
    public void updateTaskDetails(String title, String description, String status, int priority, String due_date, String update_at, int id) {
        String sql = "UPDATE tasks SET title = ?, description = ?, status = ?, priority = ?, due_date = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(7, id);
            statement.setString(1, title);
            statement.setString(2, description);
            statement.setString(3, status);
            statement.setInt(4, priority);
            statement.setString(5, due_date);
            statement.setString(6, update_at);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Task details successfully updated");
            } else {
                System.out.println("No task with ID found");
            }
        } catch(SQLException | ClassNotFoundException e) {
            System.err.println("Failed to update task: \n" + e.getMessage());
        }
    }

    // Delete a task from the database
    public void deletetask(int taskID) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, taskID);
            statement.executeUpdate();
            System.out.println("Successfully deleted task");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was a problem deleting the task: \n" + e.getMessage());
        }
    }
}
