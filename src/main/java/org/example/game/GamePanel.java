package org.example.game;
import org.example.combat.enemy;
import javax.swing.*;
import java.awt.*;
import org.example.combat.character;

public class GamePanel extends JPanel {

    private GameState state;

    public GamePanel(GameState state) {
        this.state = state;

        setPreferredSize(new Dimension(960, 540));
        setFocusable(true);
        requestFocusInWindow();

        // TODO: add key listeners, movement, combat, etc.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TODO: draw player, enemies, map, etc.
    }

    // Example: call this when an enemy is defeated
    public void onEnemyDefeated(enemy enemy) {
        enemy.setDefeated(true);
        SaveManager.save(state);
    }

    // Example: call this when player changes zone
    public void onZoneChange(int newZone) {
        state.currentZone = newZone;
        SaveManager.save(state);
    }

    // Example: call this when player learns a new ability
    public void onAbilityLearned(String ability, int damage) {
        state.russel.add_ability(ability, damage);
        SaveManager.save(state);
    }
}