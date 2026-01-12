package org.example.visuals;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Character implements Movable {

    private BufferedImage idleImage;
    private BufferedImage[] walkRight = new BufferedImage[2];
    private BufferedImage[] walkLeft = new BufferedImage[2];

    private int x, y;
    private int scale;
    private int frameIndex = 0, frameCounter = 0;
    private boolean isIdle = true;

    public BufferedImage getIdleImage() { return idleImage; }

    public Character(int startX, int startY, int scale) {
        this.x = startX;
        this.y = startY;
        this.scale = scale;

        try {
            idleImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/russelidle.png"));
            walkRight[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/russelright1.png"));
            walkRight[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/russelright2.png"));
            walkLeft[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/russelleft1.png"));
            walkLeft[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/russelleft2.png"));
        } catch (Exception e) {
            System.out.println("Error loading character images!");
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, boolean movingLeft, boolean movingRight) {
        BufferedImage current = isIdle ? idleImage :
                movingRight ? walkRight[frameIndex] :
                        movingLeft ? walkLeft[frameIndex] : idleImage;

        if (current != null) {
            g2d.drawImage(current,
                    x, y,
                    current.getWidth() * scale,
                    current.getHeight() * scale,
                    null);
        }

        if (!isIdle) {
            frameCounter++;
            if (frameCounter % 8 == 0) {
                frameIndex = (frameIndex + 1) % 2;
            }
        }
    }

    public void move(int dx) { this.x += dx; }
    public void setIdle(boolean idle) { this.isIdle = idle; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) { this.x = x; this.y = y; }
}
