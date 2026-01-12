package org.example.menu;

import javax.swing.*;

import org.example.client.AuthClient;

import java.awt.*;

public class LoginScreen extends JFrame {

    private AuthClient authClient = new AuthClient();

    public LoginScreen() {
        setTitle("Login - Russel's Sword");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JLabel userLabel = new JLabel("Email:");
        JTextField userText = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordText = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        // --- LOGIN ACTION ---
        loginButton.addActionListener(e -> {
            String email = userText.getText();
            String pass = new String(passwordText.getPassword());

            // Call Spring Boot API
            int userId = authClient.login(email, pass);

            if (userId != -1) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                
                // OPEN THE MAIN MENU WITH THE USER ID
                new menu(userId); 
                
                // Close Login Screen
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- REGISTER ACTION ---
        registerButton.addActionListener(e -> {
            String email = userText.getText();
            String pass = new String(passwordText.getPassword());
            boolean success = authClient.register(email, pass);
            if (success) {
                JOptionPane.showMessageDialog(this, "Account created! Please login.");
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed (Email taken?)", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}