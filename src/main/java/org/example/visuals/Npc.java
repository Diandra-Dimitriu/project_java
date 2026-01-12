package org.example.visuals;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Npc {
    private BufferedImage image;
    private int x, y;
    private int scale;

    private boolean hasTalked = false;

    public Npc(String spriteFile, int startX, int startY, int scale) {
        this.x = startX;
        this.y = startY;
        this.scale = scale;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(spriteFile));
        } catch (Exception e) {
            System.out.println("Error loading NPC sprite: " + spriteFile);
            e.printStackTrace();
        }
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public BufferedImage getImage() { return image; }

    // New
    public boolean hasTalked() { return hasTalked; }
    public void setTalked(boolean talked) { hasTalked = talked; }

    public void draw(Graphics2D g2d) {
        if (image != null) {
            int width = image.getWidth() * scale;
            int height = image.getHeight() * scale;
            g2d.drawImage(image, x, y, width, height, null);
        }
    }
}
