package me.rubl.gbhomework.java_advanced.lesson_one;

import java.awt.*;

public class Ball extends Sprite {

    private final Color color;
    private float vX;
    private float vY;

    Ball(float x, float y) {
        halfHeight = 20 + (float) (Math.random() * 50f);
        halfWidth = halfHeight;
        color = new Color(
                (int) (Math.random() * 255), // R
                (int) (Math.random() * 255), // G
                (int) (Math.random() * 255)  // B
        );

        this.x = x;
        this.y = y;

        vX = (float) (200f - (Math.random() * 400f));
        vY = (float) (200f - (Math.random() * 400f));
    }

    @Override
    void update(GameCanvas canvas, float deltaTime) {

        x += vX * deltaTime;//S = vt;
        y += vY * deltaTime;

        if (getLeft() < canvas.getLeft()) {
            setLeft(canvas.getLeft());
            vX = -vX;
        }
        if (getRight() > canvas.getRight()) {
            setRight(canvas.getRight());
            vX = -vX;
        }
        if (getTop() < canvas.getTop()) {
            setTop(canvas.getTop());
            vY = -vY;
        }
        if (getBottom() > canvas.getBottom()) {
            setBottom(canvas.getBottom());
            vY = -vY;
        }
    }

    @Override
    void render(GameCanvas canvas, Graphics g) {
        g.setColor(color);
        g.fillOval((int) getLeft(), (int) getTop(),
                (int) getWidth(), (int) getHeight()
        );
    }
}