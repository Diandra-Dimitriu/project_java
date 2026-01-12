package org.example.game;

import org.example.combat.character;
import org.example.database.*;
import org.example.visuals.Npc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameLoader {

    // 1. LOAD CURRENT ZONE
    public int loadCurrentZone(int userId) {
        // UserProgressDAO handles the query. 
        // If user has no progress, your DAO should return 0 or 1 (default).
        return UserProgressDAO.getCurrentZone(userId);
    }

    // 2. LOAD ABILITIES (Into an existing character)
    public void loadAbilities(character russel, int userId) {
        try {
            Map<String, Integer> loadedAbilities = AbilityDAO.getAbilities(userId);
            
            // Only add if we actually found something
            if (loadedAbilities != null && !loadedAbilities.isEmpty()) {
                russel.add_ability(loadedAbilities);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Warning: Could not load abilities. Keeping defaults.");
        }
    }

    // 3. LOAD ZONE DATA (With Crash Prevention)
    public ZoneData loadZoneData(int zoneId) {
        ZoneData data = ZoneDAO.getZone(zoneId);
        
        // --- CRITICAL FIX: NULL CHECK ---
        // If the database returns null (e.g., Zone 0 isn't in the DB yet),
        // we return a dummy "Training Field" so the game doesn't crash.
        if (data == null) {
            System.out.println("Error: Zone " + zoneId + " not found in DB. Using fallback.");
            return new ZoneData(0, "Training Field", 1, -1);
        }
        
        return data;
    }

    // 4. LOAD NPCS (Required by menu.java)
    public List<Npc> loadNpcs(int zoneId) {
        NpcDAO npcDao = new NpcDAO();
        try {
            return npcDao.getNpcsForZone(zoneId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list on error
        }
    }
}