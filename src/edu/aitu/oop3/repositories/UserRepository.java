package edu.aitu.oop3.repositories;

import edu.aitu.oop3.data.IDB;
import edu.aitu.oop3.entities.User;
import java.sql.*;

public class UserRepository {
    private final IDB db;

    public UserRepository(IDB db) {
        this.db = db;
    }

    
    public void addXp(int userId, int points) {
        String sql = "UPDATE users SET xp = xp + ? WHERE id = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, points);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            System.out.println("🎉 Congratulations! You earned " + points + " XP!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setXp(rs.getInt("xp"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}