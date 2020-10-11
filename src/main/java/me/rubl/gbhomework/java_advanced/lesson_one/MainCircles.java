package me.rubl.gbhomework.java_advanced.lesson_one;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainCircles extends JFrame {

    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    Sprite[] sprites = new Sprite[10];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainCircles();
            }
        });
    }

    private MainCircles() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Circles");

        GameCanvas canvas = new GameCanvas(this);
        add(canvas);

        initApplication();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if (SwingUtilities.isLeftMouseButton(e)) {
                    addBall(e.getX(), e.getY());
                }
                if (SwingUtilities.isRightMouseButton(e)) {
                    removeBall(e.getX(), e.getY());
                }
            }
        });

        setVisible(true);
    }

    private void initApplication() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Ball(0,0);
        }
    }

    private void addBall(float eX, float eY) {

        Ball newBall = new Ball(eX, eY);

        Sprite[] tmpArray = new Sprite[sprites.length + 1];
        for (int i = 0; i < sprites.length; i++) {
            tmpArray[i] = sprites[i];
        }
        tmpArray[tmpArray.length - 1] = newBall;

        sprites = tmpArray;

        repaint();
    }

    private void removeBall(float eX, float eY) {

        boolean isBallClicked = false;
        int removeIndex = 0;

        for (int i = 0; i < sprites.length; i++) {
            if (eX >= sprites[i].getLeft() && eX <= sprites[i].getRight() &&
                    eY >= sprites[i].getTop() && eY <= sprites[i].getBottom()) {

                isBallClicked = true;
                removeIndex = i;
                break;
            }
        }

        if (isBallClicked) {
            Sprite[] tmpArray = new Sprite[sprites.length - 1];
            int moveIndex = 0;
            for (int i = 0; i < sprites.length; i++) {
                if (i != removeIndex) {
                    tmpArray[moveIndex] = sprites[i];
                    moveIndex++;
                }
            }
            sprites = tmpArray;

            repaint();
        }
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].update(canvas, deltaTime);
        }
    }

    private void render(GameCanvas canvas, Graphics g) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].render(canvas, g);
        }
    }
}
