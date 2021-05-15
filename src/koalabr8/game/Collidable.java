package koalabr8.game;

import java.awt.*;

public interface Collidable {

    Rectangle getHitbox();

    void setX(int x);

    void setY(int y);

    int getX();
    int getY();

    void drawImage(Graphics2D g);
}