package de.frogger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Flow on 18.05.2017.
 */

public class Game extends JFrame {
    //define sizes of background components
    private int highway_length = 800;
    private int street_width = 150;
    private int street_count = 4;
    private int highway_width = street_width*street_count;
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

}
