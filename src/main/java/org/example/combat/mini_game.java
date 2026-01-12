package org.example.combat;

import java.util.Random;

public class mini_game {

    // Helper to access output methods
    private static output outputD = new output();

    // Returns true if all hits are successful
    public static boolean playRhythmEvent() {
        Random rng = new Random();

        int rounds = 3;
        long window = 1000; // Time window to hit the key (ms)

        outputD.display("DODGE! Press SPACE when you see \"NOW!\"");
        sleepFor(1500);

        for (int i = 1; i <= rounds; i++) {
            outputD.display("Round " + i + "/" + rounds + "...");

            // 1. Wait a random time before the signal
            long delay = 1000 + rng.nextInt(1000);
            sleepFor(delay);

            // 2. Clear any accidental previous presses
            input.rhythmHit = false;

            // 3. Display the Signal
            outputD.display("NOW!");

            // 4. Wait for player input
            long startTime = System.currentTimeMillis();
            boolean hit = false;

            while (System.currentTimeMillis() - startTime < window) {
                // Check if user pressed Enter (Flag set in Background.java)
                if (input.rhythmHit) {
                    hit = true;
                    break;
                }
                try { Thread.sleep(10); } catch (Exception e) {}
            }

            if (!hit) {
                outputD.display("✘ TOO SLOW!");
                sleepFor(1000);
                return false; // Fail immediately on miss
            } else {
                outputD.display("✔ HIT!");
            }
        }

        // All rounds passed
        return true;
    }

    private static void sleepFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}