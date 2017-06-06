package de.frogger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Flow on 18.05.2017.
 */

public class Game extends JFrame {
    //define sizes of background components
    private int highway_width = 800;
    private int street_height = 100;
    private int street_count = 4;
    private int highway_height = street_height*street_count;
    private Image frog;


    //car images - left / right
    private Image leftCars[] = new Image[2];
    private Image rightCars[] = new Image[2];

    Game(String title) {
        this.setSize(800,600);
        this.setTitle(title);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*for (int carCount = 0; carCount < 2; carCount++ ){
            leftCars[carCount] = getImage("cars/leftcar"+carCount+".jpg");
            rightCars[carCount] = getImage("cars/rightcar"+carCount+".jpg");
        }

        frog = getImage ("frog/frogger.jpg");*/
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.decode("#5B7E77"));
        g.fillRect(0,0,highway_width, 600);
        g.setColor(Color.decode("#A0A08E"));
        g.fillRect(0,100, highway_width, highway_height+40);
        g.setColor(Color.decode("#372B2D"));
        g.fillRect(0, 300, 800, 40);
        g.setColor(Color.white);
        for (int i=0; i<9; i++) {
            g.fillRect(i*100, 200, 50, 10);
            g.fillRect(i*100, 440, 50, 10);
        }

    }
}
