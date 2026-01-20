package edu.aitu.oop3.repositories;

import edu.aitu.oop3.data.IDB;
import edu.aitu.oop3.entities.Task;
import edu.aitu.oop3.exceptions.TaskNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements ITaskRepository {
    private final IDB db;

    public TaskRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createTask(Task task) {
        String sql = "INSERT INTO tasks (title, description, deadline, project_id, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDeadline());
            stmt.setInt(4, task.getProjectId());
            stmt.setString(5, "PENDING");

            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setDeadline(rs.getTimestamp("deadline"));
                t.setStatus(rs.getString("status"));
                t.setProjectId(rs.getInt("project_id"));
                tasks.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Task t = new Task();
                t.setId(rs.getInt("id"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setDeadline(rs.getTimestamp("deadline"));
                t.setStatus(rs.getString("status"));
                t.setProjectId(rs.getInt("project_id"));
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteTask(int id) throws TaskNotFoundException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new TaskNotFoundException("Could not delete: Task with ID " + id + " does not exist.");
            }
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, description = ?, deadline = ?, status = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setTimestamp(3, task.getDeadline());
            stmt.setString(4, task.getStatus());
            stmt.setInt(5, task.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}