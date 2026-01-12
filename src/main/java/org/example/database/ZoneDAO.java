package org.example.database;

import java.sql.*;

public class ZoneDAO {

    public static ZoneData getZone(int zoneId) {
        String sql = "SELECT id, name, difficulty_level, enemy_id FROM Zone WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, zoneId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ZoneData(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("difficulty_level"),
                        rs.getInt("enemy_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // zone not found or DB error
    }
}