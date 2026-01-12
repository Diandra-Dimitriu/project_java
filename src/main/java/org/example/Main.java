package org.example;

import org.example.menu.LoginScreen; // Import LoginScreen
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import javax.swing.SwingUtilities;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        
        // Start Spring Boot
        new SpringApplicationBuilder(Main.class)
                .headless(false)
                .run(args);

        // Start GUI
        SwingUtilities.invokeLater(() -> {
           
            new LoginScreen(); 
        });
    }
}