package org.example.combat;
import java.util.HashMap;
import java.util.Map;


public class character {
    protected int health ;
    protected int level ;
    protected Map<String, Integer> abilities = new HashMap<>();
    protected String[] dialogue = new String[3];
    protected boolean can_use_shield = false;
    protected String name = "Russel";

    // Status effects
    protected boolean stunned = false;
    protected boolean weakened = false;

    output outputD = new output();

    public String get_name(){
        return name;
    }
    public void display_dialogue(int a) {
        outputD.display(dialogue[a]);
    }

    public int get_health() {
        return health;
    }
    public void damage(int dmg) {
        health = health - dmg;
    }
    public void heal(int original) {
        health = health + original/3;
        if (health > original) {
            health = original;
        }
    }

    public void level_up(int original) {
        health = original + 25;
        level = level + 1;
    }
    public int get_level() {
        return level;
    }

    public Map<String, Integer> get_abilities(){
        return abilities;
    }
    public void add_ability(String ability, int damage){
        abilities.put(ability, damage);
    }
    public void add_ability(Map<String, Integer> abilitiesDropped) {
        abilities.putAll(abilitiesDropped);
    }

    public boolean get_shield(){
        return can_use_shield;
    }
    public void got_shield(){
        can_use_shield = true;
    }
    public void unequip_shield() {
        can_use_shield = false;
    }


    public boolean isStunned() {
        return stunned;
    }
    public void setStunned(boolean s) {
        stunned = s;
    }
    public boolean isWeakened() {
        return weakened;
    }
    public void setWeakened(boolean w) {
        weakened = w;
    }

    public void set_health_to_z() {
        health = 0;
    }

    public character() {
        this.health = 100;
        this.level = 1;
        abilities.put("Slash", 20);
    }
    public character(String name, int health, int level, Map<String, Integer> abilities) {
        this.name = name;
        this.health = health;
        this.level = level;
        if (abilities != null) {
            this.abilities.putAll(abilities);
        }

    }

    public int getHp() {
        return get_health();
    }

    public int getLevel() {
        return get_level();
    }

    public Map<String, Integer> getAbilities() {
        return get_abilities();
    }
}