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

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        List<Task> tasks = new ArrayList<>();
        try {
            tasks = taskDAO.getAllTasks();
        } catch (SQLException e) {
            System.err.println("❌ Error fetching tasks: " + e.getMessage());
        }

        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "task_form";
    }

    @PostMapping("/tasks")
    public String saveTask(@ModelAttribute("task") Task task) {
        // Assign an ID manually for now (auto-increment simulation)
        int nextId = taskList.size() + 1;
        task.setId(nextId);
        taskList.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        taskList.removeIf(task -> task.getId() == id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Task taskToEdit = null;

        for (Task t : taskList) {
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
        for (Task t : taskList) {
            if (t.getId() == updatedTask.getId()) {
                t.setTitle(updatedTask.getTitle());
                t.setDescription(updatedTask.getDescription());
                t.setStatus(updatedTask.getStatus());
                t.setPriority(updatedTask.getPriority());
                t.setDueDate(updatedTask.getDueDate());
                t.setUpdated_at(java.time.LocalDate.now().toString()); // Optional

                System.out.println("✏️ Updating task ID: " + updatedTask.getId());
                break;
            }
        }
        return "redirect:/tasks";
    }
}