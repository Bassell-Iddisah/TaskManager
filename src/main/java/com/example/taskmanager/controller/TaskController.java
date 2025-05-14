package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
public class TaskController {

    private static final List<Task> taskList = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskList);
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


}