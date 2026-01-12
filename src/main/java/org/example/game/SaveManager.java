package org.example.game;

import org.example.database.UserProgressDAO;
import org.example.database.EnemyDAO;
import org.example.combat.character;

public class SaveManager {

    public static void save(GameState state) {

        if (state == null) return;

        // -------------------------
        // Save current zone
        // -------------------------
        UserProgressDAO.updateZone(state.userId, state.currentZone);

        // -------------------------
        // Save abilities
        // -------------------------
        if (state.russel != null && state.russel.getAbilities() != null) {
            UserProgressDAO.updateAbilities(
                    state.userId,
                    state.russel.getAbilities()
            );
        }

        // -------------------------
        // Save HP + Level
        // -------------------------
        if (state.russel != null) {
            UserProgressDAO.updateStats(
                    state.userId,
                    state.russel.getHp(),
                    state.russel.getLevel()
            );
        }

        // -------------------------
        // Save enemy defeated states
        // -------------------------
        if (state.currentEnemy != null) {
            EnemyDAO.updateEnemyState(
                    state.currentEnemy.getId(),
                    state.currentEnemy.isDefeated()
            );
        }

    }
        }
