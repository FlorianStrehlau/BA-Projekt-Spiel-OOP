package de.frogger;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


/**
 * Created by Flow on 18.05.2017.
 */

public class Game extends JFrame {
    //define sizes of background components
    private int highway_width = 800;
    private int street_height = 100;
    private int street_count = 4;
    private int highway_height = street_height * street_count;
    private Image frog;


    //car images - left / right
    private Image leftCars[] = new Image[2];
    private Image rightCars[] = new Image[2];

    Game(String title) {
        this.setSize(800, 600);
        this.setTitle(title);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*for (int carCount = 0; carCount < 2; carCount++ ){
            leftCars[carCount] = getImage("cars/leftcar"+carCount+".jpg");
            rightCars[carCount] = getImage("cars/rightcar"+carCount+".jpg");
        }

        frog = getImage ("frog/frogger.jpg");*/

        new Thread(() -> {
            while (true) {

                ///calc
                SwingUtilities.invokeLater(Game.this::repaint);

                try {
                    Thread.sleep(33);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    public void paint(Graphics g) {
        int x = 0;
        while (true) {
            g.setColor(Color.decode("#5B7E77"));
            g.fillRect(0, 0, highway_width, 600);
            g.setColor(Color.decode("#A0A08E"));
            g.fillRect(0, 100, highway_width, highway_height + 40);
            g.setColor(Color.decode("#372B2D"));
            g.fillRect(0, 300, 800, 40);
            g.setColor(Color.decode("#8A9B0F"));
            g.fillRect(400, xa, 60, 60);
            g.setColor(Color.white);
            for (int i = 0; i < 9; i++) {
                g.fillRect(i * 100, 200, 50, 10);
                g.fillRect(i * 100, 440, 50, 10);
                /*Rectangle r1 = new Rectangle(0, 0, 10, 10);
                Rectangle r2 = new Rectangle(9, 9, 10, 10);
                r1.intersects(r2);
                r1.setLocation(9,9);*/
            }
            g.fillRect(x * 100, 480, 50, 40);
            g.fillRect(x * 150, 250, 50, 40);
            x++;
            if (x == 8) {
                x = 0;
            }
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
            }
        }
    }

    int xa = 560;

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            xa = -1;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            xa = 1;
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }
}
