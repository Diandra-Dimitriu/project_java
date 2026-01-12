package org.example.visuals;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Enemy {

    private BufferedImage image;
    private int x, y;
    private int scale;

    public Enemy(String resourcePath, int startX, int startY, int scale) {
        this.x = startX;
        this.y = startY;
        this.scale = scale;

        try {
            var stream = getClass().getResourceAsStream(resourcePath);
            if (stream == null) {
                throw new RuntimeException("Enemy sprite not found: " + resourcePath);
            }
            image = ImageIO.read(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        if (image != null) {
            int width = image.getWidth() * scale;
            int height = image.getHeight() * scale;
            g2d.drawImage(image, x, y, width, height, null);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public BufferedImage getImage() {
        return image;
    }
}
