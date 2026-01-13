package org.example.menu;

import org.example.visuals.Background;
import javax.swing.*;
import java.awt.*;
import org.example.game.GameLoader;
import org.example.game.GameState;
import org.example.combat.character;
import org.example.combat.main_combat;

public class menu extends JFrame {
    private int currentUserId;

    public menu(int userId) {
        this.currentUserId = userId;


        setTitle("Russel's Sword");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 540);
        setResizable(false);

        JPanel background = new JPanel() {
            private Image img = new ImageIcon(getClass().getResource("/images/menu.png")).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, 960, 540, this);
            }
        };
        background.setLayout(new GridBagLayout());
        setContentPane(background);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton newGameBtn = createMenuButton("New Game");
        JButton continueBtn = createMenuButton("Continue");
        JButton exitBtn = createMenuButton("Exit");

        buttonPanel.add(newGameBtn);
        buttonPanel.add(continueBtn);
        buttonPanel.add(exitBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(150, 0, 0, 0);
        background.add(buttonPanel, gbc);

        setLocationRelativeTo(null);
        setVisible(true);

        // Exit
        exitBtn.addActionListener(e -> System.exit(0));

        // New Game
        newGameBtn.addActionListener(e -> {
            org.example.database.UserProgressDAO.createNewSave(currentUserId);
            org.example.combat.main_combat.chara = new character();
            SwingUtilities.invokeLater(() -> {
                
                JFrame gameFrame = new JFrame("Russel's Adventure");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Background bg = new Background(0, currentUserId);
                gameFrame.add(bg);
                gameFrame.pack();
                gameFrame.setResizable(false);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);

                dispose();
            });
        });

        // Continue
        continueBtn.addActionListener(e -> {
            try {
                int userIdToLoad = this.currentUserId;

                GameState state = new GameState(userId);
                state.russel = new character();
                GameLoader loader = new GameLoader();

                state.currentZone = loader.loadCurrentZone(userIdToLoad);
                loader.loadAbilities(state.russel, userIdToLoad);
                state.zoneData = loader.loadZoneData(state.currentZone);

                main_combat.chara = state.russel;

                JOptionPane.showMessageDialog(
                        this,
                        "Loaded progress:\n" +
                                "Zone: " + state.zoneData.getName() + "\n" +
                                "Difficulty: " + state.zoneData.getDifficulty() + "\n" +
                                "Abilities: " + state.russel.get_abilities(),
                        "Continue Game",
                        JOptionPane.INFORMATION_MESSAGE
                );

                SwingUtilities.invokeLater(() -> {
                    JFrame gameFrame = new JFrame("Russel's Adventure");
                    gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    Background bg = new Background(state.currentZone, userId);
                    gameFrame.add(bg);

                    gameFrame.pack();
                    gameFrame.setResizable(false);
                    gameFrame.setLocationRelativeTo(null);
                    gameFrame.setVisible(true);

                    dispose();
                });

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load progress!");
            }
        });
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Monospaced", Font.BOLD, 22));
        btn.setFocusPainted(false);

        Color baseBG = new Color(21, 41, 64);
        Color hoverBG = new Color(31, 61, 94);
        Color borderColor = new Color(179, 179, 24);

        btn.setForeground(borderColor);
        btn.setBackground(baseBG);

        btn.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(borderColor, 4),
                        BorderFactory.createEmptyBorder(10, 20, 10, 20)
                )
        );

        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setRolloverEnabled(false);
        btn.setFocusPainted(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverBG);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(baseBG);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverBG);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                btn.setBackground(baseBG);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new menu(1));
    }
}