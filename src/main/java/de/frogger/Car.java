package de.frogger;

import java.awt.*;

/**
 * Created by Flow on 18.05.2017.
 */
public class Car extends Rectangle {

    public int texture;

    public Car(int x, int y, int w, int h, int t) {
        super(x, y, w, h);
        this.texture = t;
    }

}
