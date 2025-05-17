package com.example.taskmanager.controller;

import com.example.taskmanager.DAO.TaskDAO;
import com.example.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Controller
public class TaskController {

    @Autowired
    private TaskDAO taskDAO;

    private static final List<Task> taskList = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    // Display all tasks
    @GetMapping("/tasks")
    public String listTasks(Model model) {
        try {
            List<Task> tasks = taskDAO.getAllTasks();
            model.addAttribute("tasks", tasks);
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("tasks", List.of());
        }
        return "tasks";
    }

    // Show form to create a new task
    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "task_form";
    }

    // Save new task
    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("task") Task task) throws SQLException {
        String today = LocalDate.now().toString();

        task.setCreated_at(today);
        task.setUpdated_at(today);
        try {
            taskDAO.insertTask(task);
            System.out.println("Inserted new task");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
//        taskList.removeIf(task -> task.getId() == id);
        System.out.println("Deleting task");
        taskDAO.deletetask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) throws SQLException {
        Task taskToEdit = null;

        for (Task t : taskDAO.getAllTasks()) {
            if (t.getId() == id) {
                taskToEdit = t;
                break;
            }
        }
        if (taskToEdit != null) {
            model.addAttribute("task", taskToEdit);
            return "task_form"; // reuse the same form
        } else {
            return "redirect:/tasks"; // fallback
        }
    }

    @PostMapping("/tasks/edit")
    public String updateTask(@ModelAttribute("task") Task updatedTask) {
        updatedTask.setUpdated_at(java.time.LocalDate.now().toString());

        taskDAO.updateTaskDetails(updatedTask.getTitle(), updatedTask.getDescription(), updatedTask.getStatus(), updatedTask.getPriority(), updatedTask.getDueDate(), updatedTask.getUpdated_at(), updatedTask.getId());
        System.out.println("✏️ Updated task in DB: ID " + updatedTask.getId());

        return "redirect:/tasks";
    }

}