package org.example.database;

import java.sql.*;
import java.util.Map;

public class UserProgressDAO {

    // Save or update current zone
    public static void updateZone(int userId, int zoneId) {
        String sql = """
            INSERT INTO UserProgress (user_id, current_zone_id)
            VALUES (?, ?)
            ON CONFLICT(user_id) DO UPDATE SET current_zone_id = excluded.current_zone_id
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, zoneId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save HP + Level
    public static void updateStats(int userId, int hp, int level) {
        String sql = """
            UPDATE UserProgress
            SET hp = ?, level = ?
            WHERE user_id = ?
        """;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hp);
            stmt.setInt(2, level);
            stmt.setInt(3, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save abilities (stored in RusselAbilities)
    public static void updateAbilities(int userId, Map<String, Integer> abilities) {
        String deleteSql = "DELETE FROM RusselAbilities WHERE user_id = ?";
        String insertSql = "INSERT INTO RusselAbilities (user_id, ability_name, level) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {

            // Clear old abilities
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, userId);
                deleteStmt.executeUpdate();
            }

            // Insert updated abilities
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
                    insertStmt.setInt(1, userId);
                    insertStmt.setString(2, entry.getKey());
                    insertStmt.setInt(3, entry.getValue());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load current zone
    public static int getCurrentZone(int userId) {
        String sql = "SELECT current_zone_id FROM UserProgress WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("current_zone_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // default zone
    }

    // Load HP + Level
    public static int[] getStats(int userId) {
        String sql = "SELECT hp, level FROM UserProgress WHERE user_id = ?";
        int[] stats = new int[]{100, 1}; // default HP=100, level=1

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                stats[0] = rs.getInt("hp");
                stats[1] = rs.getInt("level");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }

    // Reset progress
    public static void resetProgress(int userId) {
        String sql = "DELETE FROM UserProgress WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
    }
    public static void createNewSave(int userId) {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false); // Start Transaction

            // 1. Reset Stats (Level 1, HP 100, Zone 0)
            // We use "INSERT OR REPLACE" to handle both new and existing users
            String updateStats = """
                INSERT INTO UserProgress (user_id, current_zone_id, hp, level) 
                VALUES (?, 0, 100, 1)
                ON CONFLICT(user_id) DO UPDATE SET 
                    current_zone_id = 0,
                    hp = 100,
                    level = 1
            """;
            try (PreparedStatement stmt = conn.prepareStatement(updateStats)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // 2. Clear Defeated Bosses
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM UserBossProgress WHERE user_id = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            // 3. Reset Abilities
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM RusselAbilities WHERE user_id = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            
            // 4. Re-add default ability (MANUALLY, using the same 'conn')
            // We do this here to avoid the "Database Locked" error
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO RusselAbilities (user_id, ability_name, damage) VALUES (?, ?, ?)")) {
                stmt.setInt(1, userId);
                stmt.setString(2, "Slash");
                stmt.setInt(3, 20);
                stmt.executeUpdate();
            }

            conn.commit(); // Commit ALL changes
            System.out.println("✅ Save file successfully wiped for New Game.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}