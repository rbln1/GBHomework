package me.rubl.gbhomework.java_base.lesson_seven;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {

    // Window and settings constants
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 270;
    private static final int MIN_WIN_LENGTH = 3;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final String FIELD_SIZE_PREFIX = "Field size is: ";
    private static final String WIN_LENGTH_PREFIX = "Win length is: ";

    private final GameWindow gameWindow;

    // Radio buttons for choose game mode
    private JRadioButton humanVersusAI;
    private JRadioButton humanVersusHuman;

    // Sliders for choose field size and win line length
    private JSlider slideWinLineLength;
    private JSlider slideFieldSize;

    SettingsWindow(GameWindow gameWindow) {

        this.gameWindow = gameWindow;

        // Set settings window size
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Measuring the correct location of the settings window
        Rectangle gameWindowBounds = gameWindow.getBounds();
        int posX = (int) gameWindowBounds.getCenterX() - WINDOW_WIDTH / 2;
        int posY = (int) gameWindowBounds.getCenterY() - WINDOW_HEIGHT / 2;

        // Set settings window location
        setLocation(posX, posY);

        // Turn off resize func
        setResizable(false);

        // Set window title
        setTitle("Setting up parameters for new game");

        // Set layout manager
        setLayout(new GridLayout(10, 1));

        // Add settings widgets
        addGameModeControls();
        addFieldControls();

        // Create start game button
        JButton btnStartGame = new JButton("Start a game");

        // Add click listener to start game button
        btnStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Put all parameters to GameWindow for start game and hide settings window
                collectDataFromControlsAndStartGame();
            }
        });

        // Add start game button
        add(btnStartGame);
    }

    private void addGameModeControls() {

        add(new JLabel("Game mode: "));

        humanVersusAI = new JRadioButton("Human versus AI", true);
        humanVersusHuman = new JRadioButton("Human versus Human");

        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humanVersusAI);
        gameMode.add(humanVersusHuman);

        add(humanVersusAI);
        add(humanVersusHuman);
    }

    private void addFieldControls() {

        JLabel lbFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel lbWinLength = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);

        slideFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        slideWinLineLength = new JSlider(MIN_WIN_LENGTH, MIN_FIELD_SIZE, MIN_FIELD_SIZE);

        slideFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = slideFieldSize.getValue();
                lbFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
                slideWinLineLength.setMaximum(currentValue);
            }
        });

        slideWinLineLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lbWinLength.setText(WIN_LENGTH_PREFIX + slideWinLineLength.getValue());
            }
        });

        add(new JLabel("Choose field size"));
        add(lbFieldSize);
        add(slideFieldSize);
        add(new JLabel("Choose win length"));
        add(lbWinLength);
        add(slideWinLineLength);
    }

    private void collectDataFromControlsAndStartGame() {

        Map.GameMode gameMode;

        if (humanVersusAI.isSelected()) {
            gameMode = Map.GameMode.MODE_HUMAN_VS_AI;
        } else if (humanVersusHuman.isSelected()) {
            gameMode = Map.GameMode.MODE_HUMAN_VS_HUMAN;
        } else {
            // Throw this if chosen game mode not found
            throw new RuntimeException("Unexpected game mode!");
        }

        int fieldSize = slideFieldSize.getValue();
        int winLen = slideWinLineLength.getValue();

        gameWindow.acceptSettings(gameMode, fieldSize, fieldSize, winLen);

        setVisible(false);
    }
}