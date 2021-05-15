package koalabr8.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Saw implements Collidable{
    private int x;
    private int y;


    BufferedImage img;

    private Rectangle hitbox;



    Saw(int x, int y, BufferedImage img) {
        this.x = x;
        this.y = y;

        this.img = img;
        this.hitbox = new Rectangle(x,y,img.getWidth(),img.getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void drawImage(Graphics2D g) {
        //g2d.setColor(Color.CYAN);
        g.drawImage(this.img, this.x,this.y, null);
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
