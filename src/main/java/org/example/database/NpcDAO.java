package org.example.database;

import org.example.visuals.Npc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NpcDAO {

    public List<Npc> getNpcsForZone(int zoneId) throws SQLException {
        String sql = "SELECT sprite_path, x, y, scale FROM SideCharacter WHERE zone_id = ?";

        List<Npc> npcs = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, zoneId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String sprite = rs.getString("sprite_path");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int scale = rs.getInt("scale");

                npcs.add(new Npc(sprite, x, y, scale));
            }
        }

        return npcs;
    }
}