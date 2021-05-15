package koalabr8.game;

import koalabr8.GameConstants;

public class Move {
    Koala mover;

    String direction;
    int ticks = GameConstants.TILE_SIZE;

    Move(Koala k, String direction) {
        this.mover = k;
        this.direction = direction;
    }



    public int getTicks() {
        return this.ticks;
    }

    public Koala getMover() {
        return this.mover;
    }

    public void execute() {
        if (this.direction.equals("L")) {
            mover.moveLeft();
            this.ticks--;
        }
        else if (this.direction.equals("R")) {
            mover.moveRight();
            this.ticks--;
        }
        else if (this.direction.equals("U")) {
            mover.moveForwards();
            this.ticks--;
        }
        else if (this.direction.equals("D")) {
            mover.moveBackwards();
            this.ticks--;
        }

        this.mover.update();
    }
}
