package org.example.combat;

import java.util.Map;

public class battle {

    input inputD = new input();
    output outputD = new output();

    // Enemy instances
    Guardian guard = new Guardian();
    Warden warden = new Warden();
    Strigoi strigoi = new Strigoi();

    randomy random = new randomy();

    public void fight(enemy enem, character chara) {
        // Route to the correct specific fight method
        switch (enem.get_name()) {
            case "Guardian of Whispers":
                fight_guardian(chara);
                break;
            case "Warden of the Pyre":
                fight_warden(chara);
                break;
            case "Strigoi of Hollowdeep":
                fight_strigoi(chara);
                break;
        }
    }

    // --- GUI TURN HANDLER ---
    private void handlePlayerTurn(character chara, enemy enem) {
        boolean turnOver = false;
        Map<String, Integer> abilities_c = chara.get_abilities();

        while (!turnOver) {
            outputD.display("Your turn! Select an action.");
            outputD.showMainMenu(); // Show buttons

            String choice = inputD.waitForGUI();
            outputD.clearScreen();

            switch (choice) {
                case "ABILITY":
                    outputD.showAbilities(abilities_c); // Show ability buttons
                    String abilityKey = inputD.waitForGUI();

                    if (abilityKey.equals("BACK")) {
                        continue; // Go back to main menu
                    } else {
                        outputD.hideMenu();
                        playerAttack(chara, enem, abilityKey, abilities_c.get(abilityKey));
                        turnOver = true;
                    }
                    break;

                case "INFO":
                    enem.display_info();
                    break;

                case "SHIELD":
                    outputD.hideMenu();
                    chara.got_shield();
                    outputD.display("Shield up!");
                    turnOver = true;
                    break;

                case "HEAL":
                    outputD.hideMenu();
                    chara.heal(100); // Adjust heal amount if needed
                    outputD.display("Healed!");
                    turnOver = true;
                    break;
            }
        }
        outputD.hideMenu();
    }

    // --- AI BRAIN ---
    public String makeSmartMove(enemy enem, character player) {
        Map<String, Integer> abilities = enem.get_abilities();
        String enemyName = enem.get_name();
        int myHealth = enem.get_health();

        // 1. Kill Shot
        for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
            String move = entry.getKey();
            int dmg = entry.getValue();
            if (player.get_shield()) dmg /= 2;
            if (enem.getDamageMultiplier() > 1.0) dmg *= enem.getDamageMultiplier();
            if (dmg >= player.get_health()) return move;
        }

        // 2. Tactics
        if (enemyName.contains("Warden")) {
            if (myHealth < 50 && abilities.containsKey("Searing Scorch")) return "Searing Scorch";
            if (enem.getDamageMultiplier() <= 1.0 && abilities.containsKey("Magma Cleave")) return "Magma Cleave";
        }
        if (enemyName.contains("Strigoi")) {
            if (myHealth < 80 && abilities.containsKey("Vampiric Bite")) return "Vampiric Bite";
            if (!player.isWeakened() && abilities.containsKey("Echoing Scream")) return "Echoing Scream";
        }
        if (enemyName.contains("Guardian")) {
            if (myHealth < 40 && abilities.containsKey("Stomp")) return "Stomp";
        }

        // 3. Fallback
        String bestAbility = null;
        int maxDmg = -1;
        for (Map.Entry<String, Integer> entry : abilities.entrySet()) {
            if (entry.getValue() > maxDmg) {
                maxDmg = entry.getValue();
                bestAbility = entry.getKey();
            }
        }
        return bestAbility;
    }

    // --- ATTACK HELPER ---
    private void playerAttack(character chara, enemy enem, String abilityName, int baseDamage) {
        double multiplier = 1.0;

        if (chara.isWeakened()) {
            outputD.display("You are WEAKENED! Damage reduced by 30%.");
            multiplier -= 0.3;
            chara.setWeakened(false);
        }

        if (abilityName.equals("Slash") && random.pick(0, 1) == 0) {
            outputD.display("CRITICAL HIT! Double damage!");
            multiplier *= 2.0;
        }

        int finalDamage = (int) (baseDamage * multiplier);
        enem.damage(finalDamage);

        outputD.display("Enemy took " + finalDamage + " damage!");
        if (enem.get_health() < 0) enem.set_health_to_z();
        outputD.display("Enemy health: " + enem.get_health());
    }

    // --- FIGHT LOGIC: STRIGOI ---
    public void fight_strigoi(character chara) {
        outputD.clearScreen();
        outputD.display("Strigoi of Hollowdeep appears!");
        strigoi.display_dialogue(0);

        int original = chara.get_health();

        outputD.waitForEnter();
        outputD.clearScreen();

        while (strigoi.get_health() > 0 && chara.get_health() > 0) {
            chara.unequip_shield();
            outputD.display("Your Health: " + chara.get_health());

            if (chara.isStunned()) {
                outputD.display("You are STUNNED!");
                chara.setStunned(false);
            } else {
                handlePlayerTurn(chara, strigoi);
            }
            if (strigoi.get_health() <= 0) break;

            outputD.waitForEnter();
            outputD.clearScreen();

            // Enemy Turn
            if((randomy.pick(1, 2) % 2) == 0) strigoi.display_dialogue(randomy.pick(0, 2));

            String ability = makeSmartMove(strigoi, chara);
            int dmg = strigoi.get_abilities().get(ability);

            if (ability.equals("Vampiric Slash")) {
                outputD.display("Strigoi drains life (+10 HP)!");
                strigoi.heal(10);
            }
            if (ability.equals("Echoing Scream") && random.pick(1, 10) <= 3) {
                outputD.display("You are Weakened!");
                chara.setWeakened(true);
            }

            outputD.display("Enemy uses " + ability + "!");
            boolean passed = mini_game.playRhythmEvent();

            if (!passed) {
                if (chara.get_shield()) dmg /= 2;
                chara.damage(dmg);
                outputD.display("Hit! You took " + dmg + " damage.");
            } else {
                outputD.display("Dodged!");
            }
            if (chara.get_health() < 0) chara.set_health_to_z();

            outputD.waitForEnter();
            outputD.clearScreen();
        }
        endFight(chara, original, strigoi);
    }

    // --- FIGHT LOGIC: WARDEN ---
    public void fight_warden(character chara) {
        outputD.clearScreen();
        outputD.display("Warden of the Pyre appears!");
        warden.display_dialogue(0);

        int original = chara.get_health();

        outputD.waitForEnter();
        outputD.clearScreen();

        while (warden.get_health() > 0 && chara.get_health() > 0) {
            chara.unequip_shield();
            outputD.display("Your Health: " + chara.get_health());

            if (chara.isStunned()) {
                outputD.display("You are STUNNED!");
                chara.setStunned(false);
            } else {
                handlePlayerTurn(chara, warden);
            }
            if (warden.get_health() <= 0) break;

            outputD.waitForEnter();
            outputD.clearScreen();

            // Enemy Turn
            if((randomy.pick(1, 2) % 2) == 0) warden.display_dialogue(randomy.pick(0, 2));

            String ability = makeSmartMove(warden, chara);
            int baseDmg = warden.get_abilities().get(ability);

            if (warden.getDamageMultiplier() > 1.0) {
                outputD.display("Magma empowers the attack!");
                baseDmg = (int) (baseDmg * warden.getDamageMultiplier());
                warden.setDamageMultiplier(1.0);
            }
            if (ability.equals("Magma Cleave")) {
                outputD.display("Blade heats up! (Next attack +20%)");
                warden.setDamageMultiplier(1.2);
            }
            if (ability.equals("Searing Scorch")) {
                outputD.display("Warden heals 15 HP.");
                warden.heal(15);
            }

            outputD.display("Enemy uses " + ability + "!");
            outputD.waitForEnter();
            boolean passed = mini_game.playRhythmEvent();

            if (!passed) {
                if (chara.get_shield()) baseDmg /= 2;
                chara.damage(baseDmg);
                outputD.display("Hit! You took " + baseDmg + " damage.");
            } else {
                outputD.display("Dodged!");
            }
            if (chara.get_health() < 0) chara.set_health_to_z();

            outputD.waitForEnter();
            outputD.clearScreen();
        }
        endFight(chara, original, warden);
    }

    // --- FIGHT LOGIC: GUARDIAN ---
    public void fight_guardian(character chara) {
        outputD.clearScreen();
        outputD.display("Guardian of Whispers appears!");
        guard.display_dialogue(0);

        int original = chara.get_health();

        outputD.waitForEnter();
        outputD.clearScreen();

        while (guard.get_health() > 0 && chara.get_health() > 0) {
            chara.unequip_shield();
            outputD.display("Your Health: " + chara.get_health());

            if (chara.isStunned()) {
                outputD.display("You are STUNNED!");
                chara.setStunned(false);
            } else {
                handlePlayerTurn(chara, guard);
            }
            if (guard.get_health() <= 0) break;

            outputD.waitForEnter();
            outputD.clearScreen();

            // Enemy Turn
            if((randomy.pick(1, 2) % 2) == 0) guard.display_dialogue(randomy.pick(0, 2));

            String ability = makeSmartMove(guard, chara);
            int dmg = guard.get_abilities().get(ability);

            if (ability.equals("Slash") && random.pick(0, 1) == 0) {
                outputD.display("CRITICAL HIT! Double damage!");
                dmg *= 2;
            }
            if (ability.equals("Stomp") && random.pick(0, 1) == 0) {
                outputD.display("The ground shakes! You are STUNNED!");
                chara.setStunned(true);
            }

            outputD.display("Enemy uses " + ability + "!");
            boolean passed = mini_game.playRhythmEvent();

            if (!passed) {
                if (chara.get_shield()) dmg /= 2;
                chara.damage(dmg);
                outputD.display("Hit! You took " + dmg + " damage.");
            } else {
                outputD.display("Dodged!");
            }
            if (chara.get_health() < 0) chara.set_health_to_z();

            outputD.waitForEnter();
            outputD.clearScreen();
        }
        endFight(chara, original, guard);
    }

    // --- END FIGHT / PERSISTENCE HANDLER ---
    private void endFight(character chara, int original, enemy foe) {
        outputD.clearScreen();

        if (chara.get_health() <= 0) {
            outputD.display("You have been defeated.");
            outputD.display("GAME OVER");
            outputD.waitForEnter();
            System.exit(0); // Closes game on loss
        } else {
            outputD.display("VICTORY!");

            // This updates the STATIC character from main_combat
            chara.level_up(original);
            outputD.display("Level Up! Level: " + chara.get_level() + " | Health: " + chara.get_health());

            // Add new abilities to the STATIC character
            chara.add_ability(foe.get_abilities_dropped());
            outputD.display("You learned new abilities!");
            foe.get_abilities_dropped().forEach((key, value) -> {
                outputD.displayf("%-15s : %d%n", key, value);
            });

            outputD.waitForEnter();
        }
    }
}