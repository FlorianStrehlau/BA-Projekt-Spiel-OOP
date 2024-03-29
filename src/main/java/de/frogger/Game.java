package de.frogger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


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
    private Image frogR;
    private Image frogL;
    private Image frogD;
    private Image palm1;
    private Image palm2;
    private Image palm3;
    private Image palm4;
    private Image palm5;
    private Image palm6;
    private Image coast;
    private Image coast2;
    private Image gameOverlay;
    boolean nightTime = false;
    int x = 1;
    int y_frog = 560;
    int x_frog = 380;
    // velo = gamevelocity
    int velo = 4;
    int score = 0;
    int round = 0;
    private Sound victory = new Sound();
    int yCoast = 0;
    boolean downwards = true;
    // to adjust wave timing without thread sleep
    private final long PERIOD = 80L;
    private long lastTime = System.currentTimeMillis() - PERIOD;
    int keySwitch = 0;
    int highscore = 0;


    ArrayList<Car> al_right = new ArrayList<>();
    ArrayList<Car> al_left = new ArrayList<>();


    //car images - left / right
    private Image Cars[] = new Image[20];
    private Image Palms[] = new Image[6];


    boolean motorcycles_added = false;
    boolean cars_added = false;

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
    MenuItem option4 = new MenuItem("Nightmare");
    MenuItem option5 = new MenuItem("Overkill!");
    MenuItem option6 = new MenuItem("Deathwish x_x");

    Menu points = new Menu("Score: " + score);

    BufferedImage rendered;

    Game(String title) {
        //checking operating system (and set menu sizes)
        if (getOsName().toLowerCase().contains("windows")) {
            this.setSize(800, 645);
        } else {
            this.setSize(800, 600);
        }

        this.setTitle(title);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setVisible(true);
        this.setResizable(false);
        this.requestFocus();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Loading images
        for (int carCount = 1; carCount <= 20; carCount++) {
            Cars[carCount - 1] = getImage("car" + carCount + ".png");
        }


        frog = getImage("frogChar.png");
        frogR = getImage("Frog_r.png");
        frogL = getImage("Frog_l.png");
        frogD = getImage("Frog_d.png");
        for (int i = 1; i <= 6; i++) {
            Palms[i - 1] = getImage("palm" + i + ".png");
        }
        coast = getImage("coast.png");
        coast2 = getImage("coast2.png");
        gameOverlay = getImage("gameOverlay.png");
        rendered = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        int[] GapCar1 = {0, 205, 410, 590, 890};
        int[] GapCar2 = {0, 800, 510, 660};
        int[] GapCar3 = {0, 205, 410, 600, 890};
        int[] GapCar4 = {0, 125, 240, 600, 810};

        createMenuBar();

        // Initial song that plays in the background
        Sound backgroundmusic = new Sound();
        backgroundmusic.setFile("AgelessRiverExcerpt.wav");
        backgroundmusic.loop();

        for (int i = 0; i < 4; i++) {
            al_right.add(new Car(GapCar3[i], 480, 80, 40, (int) (Math.random() * 6)));
            al_left.add(new Car(GapCar1[i], 365, 80, 40, (int) (Math.random() * 6)));
            al_right.add(new Car(GapCar4[i], 240, 80, 40, (int) (Math.random() * 6)));
            al_left.add(new Car(GapCar2[i], 120, 80, 40, (int) (Math.random() * 6)));
        }
        al_left.add(new Car(230, 120, 180, 50, (int) (Math.random() * 2) + 6));

        al_right.add(new Car(GapCar3[4], 480, 80, 40, (int) (Math.random() * 6)));
        al_left.add(new Car(GapCar1[4], 365, 80, 40, (int) (Math.random() * 6)));
        al_right.add(new Car(GapCar4[4], 240, 80, 40, (int) (Math.random() * 6)));


        /*
         * Actionlistener anonymous class
         * adjusts rounds and resets score on difficulty select
         */
        option1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                velo = 6;
                score = 0;
                round = 3;
                y_frog = 560;
                x_frog = 380;
                points.setLabel("Score: " + score);
            }
        });

        option2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (motorcycles_added == false)
                    addMotorcycles();
                motorcycles_added = true;
                velo = 8;
                score = 0;
                round = 5;
                y_frog = 560;
                x_frog = 380;
                points.setLabel("Score: " + score);
            }
        });

        option3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (motorcycles_added == false)
                    addMotorcycles();
                motorcycles_added = true;
                velo = 9;
                score = 0;
                round = 7;
                y_frog = 560;
                x_frog = 380;
                points.setLabel("Score: " + score);
            }
        });

        option4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (motorcycles_added == false)
                    addMotorcycles();
                motorcycles_added = true;
                velo = 12;
                score = 0;
                round = 9;
                y_frog = 560;
                x_frog = 380;
                points.setLabel("Score: " + score);
            }
        });

        option5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (motorcycles_added == false)
                    addMotorcycles();
                motorcycles_added = true;
                velo = 25;
                score = 0;
                round = 15;
                y_frog = 560;
                x_frog = 380;
                points.setLabel("Score: " + score);
            }
        });

        option6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (motorcycles_added == false)
                    addMotorcycles();
                motorcycles_added = true;
                velo = 100;
                score = 0;
                round = 100;
                y_frog = 510;
                x_frog = 380;
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

                zeichneFrogger(rendered.getGraphics());
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
        option.add(option5);
        option.add(option6);

        mb.add(points);
    }

    @Override
    public void update(Graphics g) {
    }

    static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    @Override
    public void paint(Graphics g) {
        //checking operating system (and set world positioning)
        if (getOsName().toLowerCase().contains("windows")) {
            g.drawImage(rendered, 0, 40, null);
        } else {
            g.drawImage(rendered, 0, 0, null);
        }
    }

    public void zeichneFrogger(Graphics g) {


        if (nightTime) {
            g.setColor(Color.decode("#5a4f2c"));
            g.fillRect(0, 0, highway_width, 600);
            g.setColor(Color.decode("#404039"));
            g.fillRect(0, 100, highway_width, highway_height + 40);
            g.setColor(Color.decode("#31312c"));
            g.fillRect(0, 300, 800, 40);

            g.setColor(Color.decode("#c7c7bb"));
            for (int i = 0; i < 9; i++) {
                g.fillRect(i * 100, 200, 50, 10);
                g.fillRect(i * 100, 440, 50, 10);
            }
        } else {
            g.setColor(Color.decode("#EED78F"));
            g.fillRect(0, 0, highway_width, 600);
            g.setColor(Color.decode("#A0A08E"));
            g.fillRect(0, 100, highway_width, highway_height + 40);
            g.setColor(Color.decode("#76766B"));
            g.fillRect(0, 300, 800, 40);

            g.setColor(Color.white);
            for (int i = 0; i < 9; i++) {
                g.fillRect(i * 100, 200, 50, 10);
                g.fillRect(i * 100, 440, 50, 10);
            }
        }

        /*
         * waves move slowly, without thread.sleep
         */
        if (nightTime) {
            g.drawImage(coast2, 0, yCoast, null);
        } else {
            g.drawImage(coast, 0, yCoast, null);
        }

        int[] coastRand = {15, 16, 17, 18, 19, 20};
        int coastY = (coastRand[new Random().nextInt(coastRand.length)]);


        long thisTime = System.currentTimeMillis();

        if ((thisTime - lastTime) >= PERIOD) {
            lastTime = thisTime;

            if (downwards) {
                if (yCoast <= 0) {
                    downwards = false;
                    yCoast++;
                } else {
                    yCoast--;
                }
            } else {
                if (yCoast >= coastY) {
                    downwards = true;
                    yCoast--;
                } else {
                    yCoast++;
                }
            }
        }

        if (nightTime) {
            // Palm trees south
            g.drawImage(Palms[3], 100, 530, null);
            g.drawImage(Palms[3], 470, 541, null);
            g.drawImage(Palms[4], 250, 550, null);
            g.drawImage(Palms[5], 50, 536, null);
            g.drawImage(Palms[3], 600, 543, null);
            g.drawImage(Palms[4], 680, 530, null);

            // Palm trees north
            g.drawImage(Palms[5], 80, 42, null);
            g.drawImage(Palms[3], 400, 37, null);
            g.drawImage(Palms[4], 190, 35, null);
            g.drawImage(Palms[3], 25, 41, null);
            g.drawImage(Palms[4], 604, 50, null);
            g.drawImage(Palms[4], 670, 55, null);
        } else {
            // Palm trees south
            g.drawImage(Palms[0], 100, 530, null);
            g.drawImage(Palms[0], 470, 541, null);
            g.drawImage(Palms[0], 250, 550, null);
            g.drawImage(Palms[1], 50, 536, null);
            g.drawImage(Palms[1], 600, 543, null);
            g.drawImage(Palms[2], 680, 530, null);

            // Palm trees north
            g.drawImage(Palms[0], 80, 42, null);
            g.drawImage(Palms[2], 400, 37, null);
            g.drawImage(Palms[0], 190, 35, null);
            g.drawImage(Palms[1], 25, 41, null);
            g.drawImage(Palms[1], 604, 50, null);
            g.drawImage(Palms[0], 670, 55, null);

        }


        Rectangle r_frog = new Rectangle(x_frog, y_frog, 20, 20);
        g.setColor(Color.decode("#8A9B0F"));
        //g.fillRect((int) r_frog.getX(), (int) r_frog.getY(), (int) r_frog.getWidth(), (int) r_frog.getHeight());

        //changes look of frog by pressing keys
        switch (keySwitch) {
            case 0:
                g.drawImage(frog, (int) r_frog.getX(), (int) r_frog.getY(), null);
                break;
            case 1:
                g.drawImage(frog, (int) r_frog.getX(), (int) r_frog.getY(), null);
                break;
            case 2:
                g.drawImage(frogD, (int) r_frog.getX(), (int) r_frog.getY(), null);
                break;
            case 3:
                g.drawImage(frogL, (int) r_frog.getX(), (int) r_frog.getY(), null);
                break;
            case 4:
                g.drawImage(frogR, (int) r_frog.getX(), (int) r_frog.getY(), null);
                break;
            default:
                g.drawImage(frog, (int) r_frog.getX(), (int) r_frog.getY(), null);
        }


        g.setColor(Color.white);

        //collision detection (cars - right)
        for (Car r2 : al_right) {
            //g.fillRect((int) r2.getX(), (int) r2.getY(), (int) r2.getWidth(), (int) r2.getHeight());
            g.drawImage(Cars[r2.texture + (nightTime ? 10 : 0)], (int) r2.getX() + (int) r2.getWidth(), (int) r2.getY(), (int) -r2.getWidth(), (int) r2.getHeight(), null);

            if (r2.intersects(r_frog) && runFlag) {

                gameOver();
            }
            r2.setLocation((int) r2.getX() + velo, (int) r2.getY());
            if (r2.getX() >= 980) {
                r2.setLocation(-180, (int) r2.getY());
            }
        }

        //collision detection (cars - left)
        for (Car r3 : al_left) {
            //g.fillRect((int) r3.getX(), (int) r3.getY(), (int) r3.getWidth(), (int) r3.getHeight());
            g.drawImage(Cars[r3.texture + (nightTime ? 10 : 0)], (int) r3.getX(), (int) r3.getY(), (int) r3.getWidth(), (int) r3.getHeight(), null);
            if (r3.intersects(r_frog) && runFlag) {

                g.drawString("100", 400, 100);
                gameOver();
            }
            r3.setLocation((int) r3.getX() - velo, (int) r3.getY());
            if (r3.getX() <= -180) {
                r3.setLocation(980, (int) r3.getY());
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
    public void
    keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && y_frog > 60) {
            keySwitch = 1;
            y_frog = y_frog - 50;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && y_frog < 560) {
            keySwitch = 2;
            y_frog = y_frog + 50;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && x_frog > 60) {
            x_frog = x_frog - 50;
            keySwitch = 3;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && x_frog < 760) {
            keySwitch = 4;
            x_frog = x_frog + 50;
        }
    }

    public void addMotorcycles() {
        al_right.add(new Car(620, 215, 50, 20, (int) (Math.random() * 2) + 8));
        al_right.add(new Car(400, 455, 50, 20, (int) (Math.random() * 2) + 8));
        al_left.add(new Car(240, 170, 50, 20, (int) (Math.random() * 2) + 8));
        al_left.add(new Car(800, 405, 50, 20, (int) (Math.random() * 2) + 8));
        return;
    }


    public void gameOver() {

        getGraphics().drawImage(gameOverlay, 0, 0, 800, 645, null);

        if (highscore < score) {
            highscore = score;
        }

        if (highscore > 0) {
            if (JOptionPane.showConfirmDialog(this,
                    "Highscore: " + highscore +
                            "\nDo you want to continue?", "Game Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                round = -1;
                score = -1;
                velo = 4;
                gameContinues();
            } else {
                System.exit(ABORT);
            }
        } else {
            if (JOptionPane.showConfirmDialog(this,
                    "Do you want to continue?", "Game Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                round = -1;
                score = -1;
                velo = 4;
                nightTime = false;
                gameContinues();
            } else {
                System.exit(ABORT);
            }
        }


    }

    public void gameContinues() {
        round++;
        /*
         * Determines the round and sets the difficulty level
         * and game score
         */
        if (round < 16) {
            switch (round) {
                case 1:
                    score = score + 1;
                    break;
                case 2:
                    score = score + 1;
                    break;
                case 3:
                    if (motorcycles_added == false)
                        addMotorcycles();
                    motorcycles_added = true;
                    score = score + 1;
                    velo = 6;
                    break;
                case 4:
                    nightTime = true;
                    score = score + 2;
                    break;
                case 5:
                    score = score + 2;
                    velo = 8;
                    break;
                case 6:
                    nightTime = false;
                    score = score + 3;
                    break;
                case 7:
                    score = score + 3;
                    velo = 9;
                    break;
                case 8:
                    nightTime = true;
                    score = score + 4;
                    break;
                case 9:
                    score = score + 4;
                    velo = 12;
                    break;
                case 10:
                    nightTime = false;
                    score = score + 5;
                    break;
                case 11:
                    score = score + 5;
                    break;
                case 12:
                    nightTime = true;
                    score = score + 5;
                    break;
                case 13:
                    score = score + 5;
                    break;
                case 14:
                    nightTime = false;
                    score = score + 5;
                    break;
                case 15:
                    score = score + 5;
                    velo = 25;
                    break;
                default:
                    score = score + 1;
            }
        } else {
            score = score + 10;
        }

        if (score != 0) {
            victory.setFile("FrogWin.wav");
            victory.play();
        }
        points.setLabel("Score: " + score);
        y_frog = 560;
        x_frog = 380;
    }

    Image getImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
