package org.example.combat;

import org.example.visuals.Background; // Import your Background class

import java.util.Map;
import java.util.Scanner;


public class output {
    Scanner scanner = new Scanner(System.in);

    // 1. Static reference to the GUI
    private static Background gui;

    // 2. Function to link the GUI (Called from Background.java)
    public static void setGui(Background bg) {
        gui = bg;
    }

    public void display(String text) {
        // If GUI is connected, send text there. Otherwise, print to console.
        if (gui != null) {
            gui.appendCombatText(text);
        } else {
            System.out.println(text);
        }
    }

    public void displayf(String text, String b, int a) {
        String formatted = String.format(text, b, a);
        if (gui != null) {
            gui.appendCombatText(formatted);
        } else {
            System.out.print(formatted);
        }
    }

    public void clearScreen() {
        if (gui != null) {
            gui.clearCombatText(); // Clears the text box
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public void waitForEnter() {
        if (gui != null) {
            gui.waitForEnterThread(); // Uses the GUI enter key logic
        } else {
            System.out.println("Press ENTER to continue...");
            scanner.nextLine();
        }
    }

    public void showMainMenu() {
        if (gui != null) gui.showCombatMenu();
    }

    public void showAbilities(Map<String, Integer> abilities) {
        if (gui != null) gui.showAbilityMenu(abilities);
    }

    public void hideMenu() {
        if (gui != null) gui.hideCombatMenu();
    }
}