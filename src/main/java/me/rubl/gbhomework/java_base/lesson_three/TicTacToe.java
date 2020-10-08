package me.rubl.gbhomework.java_base.lesson_three;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

    /*
        [Lesson Three]
        + 1. Полностью разобраться с кодом;
        + 2. Переделать проверку победы, чтобы она не была реализована просто набором условий.
        + 3. * Попробовать переписать логику проверки победы, чтобы она работала для поля 5х5 и количества фишек 4.
        + 4. *** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока, и пытаться выиграть сам.
     */

    private static final char MARK_HUMAN = 'X';
    private static final char MARK_AI = 'O';
    private static final char MARK_EMPTY = '_';

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();

    private static char[][] map;
    private static int winLineLength;
    private static int mapSizeX;
    private static int mapSizeY;

    public static void main(String[] args) {

        while (true) {

            initMap();
            printMap();

            while (true) {

                // Human
                humanTurn();
                printMap();
                if (gameChecks(MARK_HUMAN, "Human WIN!")) break;

                // AI
                aiTurn();
                printMap();
                if (gameChecks(MARK_AI, "AI WIN!")) break;
            }

            System.out.println("Do you wanna play next? (Y/N)");
            if (!SCANNER.next().substring(0,1).toLowerCase().equals("y")) break;
        }
    }

    private static void initMap() {
        // 5x5, winline: 4
        mapSizeX = 5;
        mapSizeY = 5;
        winLineLength = 4;

        map = new char[mapSizeY][mapSizeX];
        // init map by 'empty' marks
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                map[y][x] = MARK_EMPTY;
            }
        }
    }

    private static void printMap() {

        System.out.print("  ");
        for (int i = 0; i < mapSizeX * 2 + 1; i++) {
            // Print column number
            System.out.print((i % 2 == 0) ? " " : i / 2 + 1);
        }
        System.out.println();

        for (int i = 0; i < mapSizeY; i++) {

            // Print line number
            System.out.print(i + 1 + " |");

            for (int j = 0; j < mapSizeX; j++) {

                // Print cell value
                System.out.print(map[i][j] + "|");
            }
            // Print line number
            System.out.print(" " + (i + 1));

            System.out.println();
        }

        System.out.print("  ");
        for (int i = 0; i < mapSizeX * 2 + 1; i++) {
            // Print column number
            System.out.print((i % 2 == 0) ? " " : i / 2 + 1);
        }
        System.out.println();
    }

    // humanTurn
    private static void humanTurn() {

        int x, y;
        do {
            System.out.printf("Введите координаты хода X и Y (от 1 до %d) через пробел: ", mapSizeX);
            x = SCANNER.nextInt() - 1;
            y = SCANNER.nextInt() - 1;
        } while (!isCellValid(x, y) || !isCellEmpty(x, y));

        // Set mark to empty cell
        map[y][x] = MARK_HUMAN;
    }

    private static void aiTurn() {

        if(checkNextAITurnWin()) return;
        if(checkNextHumanTurnWin()) return;

        int x, y;

        do {
            x = RANDOM.nextInt(mapSizeX);
            y = RANDOM.nextInt(mapSizeY);
        } while (!isCellEmpty(x, y));

        map[y][x] = MARK_AI;
    }

    private static boolean isCellEmpty(int x, int y) {

        return map[y][x] == MARK_EMPTY;
    }

    private static boolean isCellValid(int x, int y) {

        return x >= 0 && x < mapSizeX && y >= 0 && y < mapSizeY;
    }

    private static boolean checkNextAITurnWin() {

        for (int i = 0; i < mapSizeY; i++) {
            for (int j = 0; j < mapSizeX; j++) {
                if (isCellEmpty(j, i)) {
                    map[i][j] = MARK_AI;

                    if (checkWin(MARK_AI))
                        return true;

                    map[i][j] = MARK_EMPTY;
                }
            }
        }
        return false;
    }

    private static boolean checkNextHumanTurnWin() {

        for (int i = 0; i < mapSizeY; i++) {
            for (int j = 0; j < mapSizeX; j++) {
                if (isCellEmpty(j, i)) {
                    map[i][j] = MARK_HUMAN;

                    if (checkWin(MARK_HUMAN)) {
                        map[i][j] = MARK_AI;
                        return true;
                    }

                    map[i][j] = MARK_EMPTY;
                }
            }
        }
        return false;
    }

    private static boolean checkWin(int c) {

        for (int i = 0; i < mapSizeX; i++) {
            for (int j = 0; j < mapSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLineLength, c)) return true;
                if (checkLine(i, j, 1, 1, winLineLength, c)) return true;
                if (checkLine(i, j, 0, 1, winLineLength, c)) return true;
                if (checkLine(i, j, 1, -1, winLineLength, c)) return true;
            }
        }
        return false;
    }

    private static boolean checkDraw() {

        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    private static boolean checkLine(int x, int y, int vx, int vy, int len, int c) {

        final int far_x = x + (len - 1) * vx;
        final int far_y = y + (len - 1) * vy;

        if (!isCellValid(far_x, far_y)) return false;

        for (int i = 0; i < len; i++) {
            if (map[y + i * vy][x + i * vx] != c)
                return false;
        }

        return true;
    }

    private static boolean gameChecks(char mark, String s) {

        if (checkWin(mark)) {
            System.out.println(s);
            return true;
        }

        if (checkDraw()) {
            System.out.println("Draw! Friendship won!");
            return true;
        }

        return false;
    }
}