package me.rubl.gbhomework.java_base.lesson_seven;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Map extends JPanel {

    enum GameMode {
        MODE_HUMAN_VS_HUMAN,
        MODE_HUMAN_VS_AI
    }

    public static final int MAP_CELL_DEFAULT_STATE = -1;
    public static final int MAP_CELL_PLAYER_STATE = 1;
    public static final int MAP_CELL_ENEMY_STATE = 2;

    int[][] map;

    public Map() {

        // Set grey color for panel background
        setBackground(new Color(0xcdcdcd));
    }

    void startNewGame(GameMode gameMode, int fieldSizeX, int fieldSizeY, int winLength) {

        clearPanel();

        System.out.printf("--- Starting new game with settings ---\n" +
                "• Game mode: %s\n" +
                "• Field size: %dx%d\n" +
                "• Win line: %d\n", gameMode, fieldSizeX, fieldSizeY, winLength);

        // init map
        map = new int[fieldSizeX][fieldSizeY];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                map[i][j] =  MAP_CELL_DEFAULT_STATE;
            }
        }
        printCurrentMapState(map);

        // set layout manager
        setLayout(new GridLayout(fieldSizeY, fieldSizeX));

        // add cells
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                JButton cellBtn = new JButton("");

                // TODO: add click action

                add(cellBtn);
            }
        }

        revalidate();
        repaint();
    }

    void clearPanel() {
        if (getComponentCount() > 0) {
            this.removeAll();
        }
    }

    void printCurrentMapState(int[][] array) {

        System.out.println("--- Current map ---");

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                System.out.print("\t" + array[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
