package com.example.taskmanager.model;

public class Task {
    private int id;
    private String title;
    private String description;
    private String status;
    private int priority;
    private String due_date;
    private String created_at;
    private String updated_at;
    private int user_id;

    public Task() {
        // required for form binding
    }

    public Task(int id, String title, String description, String status, int priority,
                String due_date, String created_at, String updated_at, int user_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.due_date = due_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_id = user_id;
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
    public String getDue_date() {
        return due_date;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public int getUser_id() {
        return user_id;
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
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    }