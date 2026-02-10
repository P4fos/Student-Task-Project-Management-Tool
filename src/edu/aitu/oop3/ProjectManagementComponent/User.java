package edu.aitu.oop3.ProjectManagementComponent;

public class User {
    private int id;
    private String username;
    private String email;
    private int xp;

    public User() {}

    public User(int id, String username, String email, int xp) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.xp = xp;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getXp() { return xp; }
    public void setXp(int xp) { this.xp = xp; }

    @Override
    public String toString() {
        int level = (xp / 300) + 1;
        return "Student: " + username + " | Level: " + level + " | XP: " + xp;
    }
}