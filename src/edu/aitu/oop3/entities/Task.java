package edu.aitu.oop3.entities;

import java.sql.Timestamp;

public class Task {
    private int id;
    private String title;
    private String description;
    private Timestamp deadline;
    private String status;
    private int projectId;
    private int userId;

    public Task() {}


    public Task(String title, String description, Timestamp deadline, int projectId, int userId) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = "PENDING";
        this.projectId = projectId;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Timestamp getDeadline() { return deadline; }
    public void setDeadline(Timestamp deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", status=" + status + "]";
    }
}