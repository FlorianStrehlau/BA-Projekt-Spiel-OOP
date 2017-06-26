package de.frogger;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


/*
 * Created by Flow on 18.05.2017.
 */

public class Game extends JFrame implements KeyListener {
    //define sizes of background components
    private int highway_width = 800;
    private int street_height = 100;
    private int street_count = 4;
    private int highway_height = street_height * street_count;
    private Image frog;
    int x = 1;
    int y_frog = 560;
    int x_frog = 380;
    // velo = gamevelocity
    int velo = 4;
    int score = 0;
    int round = 0;
    private Sound victory = new Sound();

    ArrayList<Rectangle> al_right = new ArrayList<Rectangle>();
    ArrayList<Rectangle> al_left = new ArrayList<Rectangle>();


    //car images - left / right
    private Image leftCars[] = new Image[2];
    private Image rightCars[] = new Image[2];

    boolean runFlag;

    // Menubar components
    MenuBar mb = new MenuBar();
    Menu sounds = new Menu("Music");
    MenuItem music1 = new MenuItem("Ageless River");
    MenuItem music2 = new MenuItem("Rhythms in the Clouds");
    MenuItem music3 = new MenuItem("DOAX2-2");

    Menu option = new Menu("Difficulty");
    MenuItem option1 = new MenuItem("Normal");
    MenuItem option2 = new MenuItem("Hard");
    MenuItem option3 = new MenuItem("Very Hard");
    MenuItem option4 = new MenuItem("Nightmare!");
    Menu points = new Menu("Score: " + score);


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

        // Initial song that plays in the background
        Sound backgroundmusic = new Sound();
        backgroundmusic.setFile("AgelessRiverExcerpt.wav");
        backgroundmusic.loop();


        al_right.add(new Rectangle(0, 480, 40, 40));
        al_right.add(new Rectangle(510, 480, 70, 40));
        al_right.add(new Rectangle(305, 480, 40, 40));
        al_right.add(new Rectangle(0, 240, 40, 40));
        al_right.add(new Rectangle(395, 240, 70, 40));
        al_right.add(new Rectangle(650, 240, 40, 40));

        al_left.add(new Rectangle(0, 365, 40, 40));
        al_left.add(new Rectangle(510, 365, 70, 40));
        al_left.add(new Rectangle(305, 365, 40, 40));
        al_left.add(new Rectangle(0, 120, 40, 40));
        al_left.add(new Rectangle(395, 120, 70, 40));
        al_left.add(new Rectangle(650, 120, 40, 40));

        /*
         * Actionlistener anonymous class
         * adjusts rounds and resets score on difficulty select
         */
        option1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                velo = 6;
                score = 0;
                round = 3;
                points.setLabel("Score: " + score);
            }
        });

        option2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                velo = 8;
                score = 0;
                round = 5;
                points.setLabel("Score: " + score);
            }
        });

        option3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                velo = 9;
                score = 0;
                round = 7;
                points.setLabel("Score: " + score);
            }
        });

        option4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                velo = 12;
                score = 0;
                round = 9;
                points.setLabel("Score: " + score);
            }
        });

        music1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                backgroundmusic.stopSound();
                backgroundmusic.setFile("AgelessRiverExcerpt.wav");
                backgroundmusic.loop();
            }
        });

        music2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                backgroundmusic.stopSound();
                backgroundmusic.setFile("RhythmsInTheCloudsExcerpt.wav");
                backgroundmusic.loop();
            }
        });

        music3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                backgroundmusic.stopSound();
                backgroundmusic.setFile("DOAX2-2Excerpt.wav");
                backgroundmusic.loop();
            }
        });

        new Thread(() -> {
            runFlag = true;
            while (runFlag) {

                ///calc
                SwingUtilities.invokeLater(Game.this::repaint);

                try {
                    Thread.sleep(33);
                } catch (Exception e) {
                }
            }


        }).start();
    }

    private void createMenuBar() {

        setMenuBar(mb);
        mb.add(sounds);
        sounds.add(music1);
        sounds.add(music2);
        sounds.add(music3);

        mb.add(option);
        option.add(option1);
        option.add(option2);
        option.add(option3);
        option.add(option4);

        mb.add(points);
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

        Rectangle r_frog = new Rectangle(x_frog, y_frog, 20, 20);
        g.setColor(Color.decode("#8A9B0F"));
        g.fillRect((int) r_frog.getX(), (int) r_frog.getY(), (int) r_frog.getWidth(), (int) r_frog.getHeight());

        g.setColor(Color.white);
        for (Rectangle r2 : al_right) {
            g.fillRect((int) r2.getX(), (int) r2.getY(), (int) r2.getWidth(), (int) r2.getHeight());
            if (r2.intersects(r_frog) && runFlag) {
                runFlag = false;
                gameOver();
            }
            r2.setLocation((int) r2.getX() + velo, (int) r2.getY());
            if (r2.getX() >= 800 + (int) r2.getWidth()) {
                r2.setLocation(0 - (int) r2.getWidth(), (int) r2.getY());
            }
        }

        for (Rectangle r3 : al_left) {
            g.fillRect((int) r3.getX(), (int) r3.getY(), (int) r3.getWidth(), (int) r3.getHeight());
            if (r3.intersects(r_frog) && runFlag) {
                runFlag = false;
                gameOver();
            }
            r3.setLocation((int) r3.getX() - velo, (int) r3.getY());
            if (r3.getX() <= 0 - (int) r3.getWidth()) {
                r3.setLocation(800 + (int) r3.getWidth(), (int) r3.getY());
            }
        }

        if (y_frog <= 70) {
            gameContinues();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && y_frog > 60)
            y_frog = y_frog - 50;
        if (e.getKeyCode() == KeyEvent.VK_DOWN && y_frog < 560)
            y_frog = y_frog + 50;
        if (e.getKeyCode() == KeyEvent.VK_LEFT && x_frog > 60)
            x_frog = x_frog - 50;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && x_frog < 760)
            x_frog = x_frog + 50;
    }


    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        System.exit(ABORT);
    }

    public void gameContinues() {
        round++;
        /*
         * Determines the round and sets the difficulty level
         * and game score
         */
        if (round < 10) {
            switch (round) {
                case 1:
                    score = score + 1;
                    break;
                case 3:
                    score = score + 1;
                    velo = 6;
                    break;
                case 4:
                    score = score + 2;
                    break;
                case 5:
                    score = score + 2;
                    velo = 8;
                    break;
                case 6:
                    score = score + 3;
                    break;
                case 7:
                    score = score + 3;
                    velo = 9;
                    break;
                case 8:
                    score = score + 4;
                    break;
                case 9:
                    score = score + 4;
                    velo = 12;
                default:
                    score = score + 1;
            }
        }
        else{
            score = score + 5;
        }

        victory.setFile("FrogWin.wav");
        victory.play();
        points.setLabel("Score: " + score);
        y_frog = 560;
        x_frog = 380;
    }
}
