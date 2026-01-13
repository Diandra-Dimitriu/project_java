package org.example.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // CHANGED: Use a simple path in the project root.
    // This creates 'game.db' right next to your pom.xml file.
    private static final String DB_FILE_NAME = "game.db";
    private static final String URL = "jdbc:sqlite:" + DB_FILE_NAME;

    public static Connection getConnection() throws SQLException {
        // Debug: Print location so we know where it is looking
        // System.out.println("Connecting to database at: " + new File(DB_FILE_NAME).getAbsolutePath());
        return DriverManager.getConnection(URL);
    }
}