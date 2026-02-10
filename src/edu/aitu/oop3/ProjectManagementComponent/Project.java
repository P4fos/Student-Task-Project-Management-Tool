package edu.aitu.oop3.ProjectManagementComponent;

public class Project {
    private int id;
    private String name;
    private int userId;

    public Project() {}
    public Project(String name, int userId) {
        this.name = name;
        this.userId = userId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}