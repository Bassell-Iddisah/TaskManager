package com.example.taskmanager.model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String status;
    private int priority;
    private String DueDate;
    private String created_at;
    private String updated_at;

    public Task() {
        // required for form binding
    }

    public Task(int id, String title, String description, String status, int priority,
                String DueDate, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.DueDate = DueDate;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getStatus() {
        return status;
    }
    public int getPriority() {
        return priority;
    }
    public String getDueDate() {
        return DueDate;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setDueDate(String due_date) {
        this.DueDate = due_date;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    }