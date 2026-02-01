package edu.aitu.oop3.repositories;

import edu.aitu.oop3.data.IDB;
import edu.aitu.oop3.entities.Task;
import edu.aitu.oop3.exceptions.ValidationException;
import edu.aitu.oop3.repositories.interfaces.ITaskRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements ITaskRepository {
    private final IDB db;

    public TaskRepository(IDB db) {
        this.db = db;
    }

    @Override
    public void createTask(Task task) throws ValidationException {
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new ValidationException("Task title cannot be empty!");
        }
        String sql = "INSERT INTO tasks (title, description, user_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setInt(3, task.getUserId());
            stmt.setString(4, "PENDING");
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public Task getById(int id) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Task(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getInt("user_id"), rs.getString("status"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }


    @Override
    public List<Task> getAll() { return new ArrayList<>(); }


    public Task getTaskById(int id) {
        return getById(id);
    }

    @Override
    public List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getInt("user_id"), rs.getString("status")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return tasks;
    }

    @Override
    public void deleteTask(int id) {
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tasks WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}