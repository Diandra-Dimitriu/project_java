package org.example.combat;
import java.util.HashMap;
import java.util.Map;



public abstract class enemy {
    protected int health = 0;
    protected int id;
    protected boolean defeated = false;
    protected Map<String, Integer> abilities = new HashMap<>();
    protected String[] info = new String[4];
    protected String[] dialogue = new String[3];
    protected boolean can_use_shield = false;
    protected String name;
    protected Map<String, Integer> abilities_dropped = new HashMap<>();

    // Buff for the next turn
    protected double damageMultiplier = 1.0;

    public abstract void set();

    output outputD = new output();

    public String get_name(){
        return name;
    }
    public void display_info(){
        for (String s : info) {
            outputD.display(s);
        }
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


    public void heal(int amount) {
        health += amount;
    }


    public void setDamageMultiplier(double mult) {
        this.damageMultiplier = mult;
    }
    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public Map<String, Integer> get_abilities(){
        return abilities;
    }
    public boolean get_shield(){
        return can_use_shield;
    }
    public Map<String, Integer> get_abilities_dropped(){
        return abilities_dropped;
    }
    public void set_health_to_z() {
        health = 0;
    }

    public int getId() {
        return id;
    }

    public boolean isDefeated() {
        return defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }
}