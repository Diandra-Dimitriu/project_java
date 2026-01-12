package org.example.game;

import org.example.visuals.Npc;
import org.example.database.ZoneData;
import org.example.combat.character;
import org.example.combat.enemy;


import java.util.List;

/**
 * GameState holds all data loaded from the database at runtime.
 * Everyone reads from this, but only GameLoader writes to it.
 */
public class GameState {

    // Which user is playing
    public int userId;

    // Player character
    public character russel;

    // Zone info
    public int currentZone;
    public ZoneData zoneData;

    // NPCs in the current zone
    public List<Npc> npcs;

    // Enemies in the current zone
    public enemy currentEnemy;

    public GameState() {
        // empty constructor for flexibility
    }

    public GameState(int userId) {
        this.userId = userId;
    }
}