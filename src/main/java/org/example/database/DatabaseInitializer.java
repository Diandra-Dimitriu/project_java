package org.example.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements CommandLineRunner {

  @Override
    public void run(String... args) throws Exception {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            // Standard Tables
            stmt.execute("CREATE TABLE IF NOT EXISTS UserAccount (id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, password TEXT);");
            stmt.execute("CREATE TABLE IF NOT EXISTS UserProgress (user_id INTEGER PRIMARY KEY, current_zone_id INTEGER DEFAULT 0, hp INTEGER DEFAULT 100, level INTEGER DEFAULT 1);");
            stmt.execute("CREATE TABLE IF NOT EXISTS RusselAbilities (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, ability_name TEXT, damage INTEGER);");
            stmt.execute("CREATE TABLE IF NOT EXISTS Zone (id INTEGER PRIMARY KEY, name TEXT, difficulty_level INTEGER, enemy_id INTEGER);");
            
            // --- THIS IS THE CRITICAL MISSING TABLE FOR BOSSES ---
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS UserBossProgress (
                    user_id INTEGER,
                    zone_id INTEGER,
                    defeated BOOLEAN DEFAULT 0,
                    PRIMARY KEY (user_id, zone_id),
                    FOREIGN KEY(user_id) REFERENCES UserAccount(id)
                );
            """);
            // -----------------------------------------------------

            System.out.println("✅ Database tables (including UserBossProgress) checked/created.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}