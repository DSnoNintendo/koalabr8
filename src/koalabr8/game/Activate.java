package koalabr8.game;

import koalabr8.GameConstants;

public class Activate extends Move{
    int ticks = 1;
    Koala activator;
    int destinationX;
    int destinationY;

    Activate(Koala k, String direction) {
        super(k,direction);
        this.activator = k;
        destinationX = activator.getX();
        destinationY = activator.getY();
    }

    public int getTicks() {
        return this.ticks;
    }

    public Koala getActivator() {
        return this.activator;
    }

    public void getActivationCoords() {
        switch (activator.getDirection()) {
            case "U":
                destinationY -= GameConstants.TILE_SIZE;
                break;
            case "D":
                destinationY += GameConstants.TILE_SIZE;
                break;
            case "R":
                destinationX += GameConstants.TILE_SIZE;
                break;
            case "L":
                destinationX -= GameConstants.TILE_SIZE;
                break;

        }
    }

    public void execute() {
        getActivationCoords();

        for (Collidable c : MapLayout.getCollidables()) {
            if (c instanceof Switch) {
                if ( destinationX == c.getX() && destinationY == c.getY() ) {
                    ((Switch) c).activate();
                }
            }
        }

        this.ticks--;

        this.activator.update();
    }
}
