package de.frogger;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 * Created by Flow on 18.05.2017.
 */

public class Game extends JFrame implements KeyListener {
    //define sizes of background components
    private int highway_width = 800;
    private int street_height = 100;
    private int street_count = 4;
    private int highway_height = street_height * street_count;
    private Image frog;
    int x = 0;
    int x2 = 8;


    //car images - left / right
    private Image leftCars[] = new Image[2];
    private Image rightCars[] = new Image[2];

    boolean runFlag;

    // Menubar components
    MenuBar mb= new MenuBar();
    Menu sounds= new Menu("Sounds");
    MenuItem musik= new MenuItem("Musik");

    Menu option= new Menu("Option");
    MenuItem option1 = new MenuItem("Option1");
    MenuItem option2= new MenuItem("Option2");
    MenuItem option3= new MenuItem("Option3");
    MenuItem option4= new MenuItem("Option4");

    Game(String title) {
        this.setSize(800, 600);
        this.setTitle(title);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
        this.requestFocus();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*for (int carCount = 0; carCount < 2; carCount++ ){
            leftCars[carCount] = getImage("cars/leftcar"+carCount+".jpg");
            rightCars[carCount] = getImage("cars/rightcar"+carCount+".jpg");
        }

        frog = getImage ("frog/frogger.jpg");*/

        createMenuBar();

        new Thread(() -> {
            runFlag = true;
            while (runFlag) {

                ///calc
                SwingUtilities.invokeLater(Game.this::repaint);

                try {
                    Thread.sleep(600);
                } catch (Exception e) {
                }
            }
        }).start();
    }

    private void createMenuBar(){

        setMenuBar(mb);
        mb.add(sounds);

        sounds.add(musik);

        mb.add(option);
        option.add(option1);
        option1.setActionCommand("210");
        option.add(option2);
        option2.setActionCommand("220");
        option.add(option3);
        option3.setActionCommand("230");
        option.add(option4);

    }

    @Override
    public void update(Graphics g) {
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.decode("#5B7E77"));
        g.fillRect(0, 0, highway_width, 600);
        g.setColor(Color.decode("#A0A08E"));
        g.fillRect(0, 100, highway_width, highway_height + 40);
        g.setColor(Color.decode("#372B2D"));
        g.fillRect(0, 300, 800, 40);

        g.setColor(Color.white);
        for (int i = 0; i < 9; i++) {
            g.fillRect(i * 100, 200, 50, 10);
            g.fillRect(i * 100, 440, 50, 10);
        }

        Rectangle r_frog = new Rectangle(400, xa, 20, 20);
        g.setColor(Color.decode("#8A9B0F"));
        g.fillRect((int) r_frog.getX(), (int) r_frog.getY(), (int) r_frog.getWidth(), (int) r_frog.getHeight());

        Rectangle r1 = new Rectangle(x * 100, 480, 50, 40);
        Rectangle r2 = new Rectangle(x * 150, 240, 50, 40);

        g.setColor(Color.white);
        g.fillRect((int) r1.getX(), (int) r1.getY(), (int) r1.getWidth(), (int) r1.getHeight());
        g.fillRect((int) r2.getX(), (int) r2.getY(), (int) r2.getWidth(), (int) r2.getHeight());

        if ((r1.intersects(r_frog) || r2.intersects(r_frog)) && runFlag) {
            runFlag = false;
            gameOver();
        }
        //r1.setLocation(9, 9);

        x++;
        if (x >= 8) {
            x = 0;
        }
    }

    int xa = 560;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            xa = xa - 50;
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            xa = xa + 50;
    }


    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }
}
