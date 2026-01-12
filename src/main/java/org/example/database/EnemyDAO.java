package org.example.database;

import org.example.combat.enemy;
import java.sql.*;

public class EnemyDAO {

    public static boolean isBossDefeated(int userId, int zoneId) {
        String sql = "SELECT defeated FROM UserBossProgress WHERE user_id = ? AND zone_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, zoneId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBoolean("defeated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public static void markBossDefeated(int userId, int zoneId) {
        String sql = "INSERT OR REPLACE INTO UserBossProgress (user_id, zone_id, defeated) VALUES (?, ?, 1)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            stmt.setInt(2, zoneId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load the single enemy assigned to a zone
    public static enemy getEnemyByZone(int zoneId) {
        String sql = "SELECT id, defeated FROM enemy WHERE zone_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, zoneId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                boolean defeated = rs.getBoolean("defeated");

                // Anonymous subclass of abstract enemy
                enemy e = new enemy() {
                    @Override
                     public void set() {
                        // Empty implementation to satisfy abstract method
                    }
                };

                // Use setter methods to avoid protected access errors
                e.setDefeated(defeated);

                // If you want to store the ID, use reflection or add a public setter in enemy.java
                // For now, we skip setting ID to avoid access violation

                return e;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Save enemy defeated state
    public static void updateEnemyState(int enemyId, boolean defeated) {
        String sql = "UPDATE enemy SET defeated = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, defeated);
            stmt.setInt(2, enemyId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}