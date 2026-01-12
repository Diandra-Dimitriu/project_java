package org.example.combat;

public class input {
    // This variable stores the text from the button the user clicked
    public static volatile String receivedCommand = null;
    public static volatile boolean rhythmHit = false;

    // The battle logic calls this to wait until a button is clicked
    public String waitForGUI() {
        receivedCommand = null; // Reset previous command
        while (receivedCommand == null) {
            try {
                Thread.sleep(10); // Wait 10ms to prevent CPU burning
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return receivedCommand;
    }

    // The GUI (Background.java) calls this when a button is clicked
    public static void sendInput(String command) {
        receivedCommand = command;
    }

    // Legacy support methods (can be removed or kept empty to avoid errors in other files)
    public String readInput_line() { return waitForGUI(); }
    public int readInt() { return 0; }
}