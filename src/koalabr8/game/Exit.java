package koalabr8.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Exit implements Collidable{
    private int x;
    private int y;
    private boolean activated = false;


    BufferedImage sprite;
    BufferedImage img;
    BufferedImage transparent;

    private Rectangle hitbox;



    Exit(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;

        this.sprite = img;
        this.hitbox = new Rectangle(x,y,img.getWidth(),img.getHeight());
        this.img = img;

    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    boolean isActivated() { return activated; }

    void activate() {
        if (!activated) {
            activated = true;
            img = transparent;
        }

    }

    void reset() {
        activated = false;
        this.img = this.sprite;
    }

    public void drawImage(Graphics2D g) {
        //g2d.setColor(Color.CYAN);
        g.drawImage(this.img, this.x,this.y, null);
    }

    public void checkCollision(Collidable c) {

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int n) {
        this.y = n;
    }

    public void setX(int n) {
        this.x = n;
    }
}
