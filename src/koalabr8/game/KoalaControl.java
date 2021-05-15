/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koalabr8.game;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author anthony-pc
 */
public class KoalaControl implements KeyListener {

    private Koala k;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int activate;
    
    public KoalaControl(Koala k, int up, int down, int left, int right, int activate) {
        this.k = k;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.activate = activate;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.k.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.k.toggleDownPressed();

        }
        if (keyPressed == left) {
            this.k.toggleLeftPressed();

        }
        if (keyPressed == right) {
            this.k.toggleRightPressed();

        }
        if (keyPressed == activate) {
            this.k.toggleActivatePressed();

        }
        

    }


    @Override
    public void keyReleased(KeyEvent ke) {
    }


}
