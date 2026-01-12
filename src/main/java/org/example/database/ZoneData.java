package org.example.database;

public class ZoneData {
    private int id;
    private String name;
    private int difficulty;
    private int enemyID;

    public ZoneData(int id, String name, int difficulty, int enemyID) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.enemyID=enemyID;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDifficulty() { return difficulty; }
    public int getEnemyId() { return enemyID; }

}