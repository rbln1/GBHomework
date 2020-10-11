package me.rubl.gbhomework.java_advanced.lesson_one;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Background {

    private final float TONE_MIN = 0;
    private final float TONE_MAX = 1;
    private final float TONE_DELTA = 0.05f;
    private final float SATURATION = 0.5f;
    private final float BRIGHTNESS = 0.5f;

    private Color color;
    private float tone;

    public Background() {

        Random random = new Random();

        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        color = new Color(r, g, b);

        tone = random.nextFloat();
    }

    public void changeBackgroundOfPanel(JPanel panel, float deltaTime) {

        tone += deltaTime * TONE_DELTA;

        if (tone > TONE_MAX) {
            tone = TONE_MIN;
        }

        color = Color.getHSBColor(tone, SATURATION, BRIGHTNESS);

        panel.setBackground(color);
    }
}
