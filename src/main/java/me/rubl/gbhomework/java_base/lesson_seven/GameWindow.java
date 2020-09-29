package me.rubl.gbhomework.java_base.lesson_seven;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {

    // Window size and position constants
    private static final int WIN_WIDTH = 512;
    private static final int WIN_HEIGHT = 512;
    private static final int WIN_POS_X = 512;
    private static final int WIN_POS_Y = 256;

    // JPanel
    private Map map;

    GameWindow() {

        // Finish app by press [X]
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set window size (width, height)
        setSize(WIN_WIDTH, WIN_HEIGHT);

        // Set window position
        setLocation(WIN_POS_X, WIN_POS_Y);

        // Locate window to center of display
        setLocationRelativeTo(null);

        // Set window title
        setTitle("Game X/O");

        // Turn off resize func
        setResizable(false);

        // Create buttons for bottom panel
        JButton btnStart = new JButton("Start game");
        JButton btnStop = new JButton("Exit");

        // Create bottom panel
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridLayout(1, 2));

        // Add created buttons to bottom panel
        panelBottom.add(btnStart);
        panelBottom.add(btnStop);

        // Initialize panel Map
        map = new Map();

        // Create settings window
        SettingsWindow settings = new SettingsWindow(this);

        // Add click listeners to bottom buttons
        btnStart.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                settings.setVisible(true);
            }
        });
        btnStop.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add panels to window
        add(map, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // Show window
        setVisible(true);
    }

    // Entering parameters from the settings window
    public void acceptSettings(Map.GameMode gameMode, int fieldSizeX, int fieldSizeY, int winLength) {

        map.startNewGame(gameMode, fieldSizeX, fieldSizeY, winLength);
    }
}