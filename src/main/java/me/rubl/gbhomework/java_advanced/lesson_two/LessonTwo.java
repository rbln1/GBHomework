package me.rubl.gbhomework.java_advanced.lesson_two;

import me.rubl.gbhomework.java_advanced.lesson_two.exceptions.IncorrectMatrixSizeException;
import me.rubl.gbhomework.java_advanced.lesson_two.exceptions.InvalidMatrixElementException;

public class LessonTwo {

    public static final int MATRIX_ROWS_LENGTH = 4;
    public static final int MATRIX_COLUMNS_LENGTH = 4;
    public static final String ROW_SPLITTER = "\n";
    public static final String COLUMN_SPLITTER = " ";

    private static String taskLine = "1 3 1 2\n2 3 2 2\n5 6 7 1\n300 3 1 0";

    public static void main(String[] args) {

        try {
            String[][] matrix = parseLine(taskLine);

            printMatrix(matrix);

            System.out.println("Half of summ: " + halfOfSumm(matrix));
        } catch (IncorrectMatrixSizeException | InvalidMatrixElementException e) {
            e.printStackTrace();
        }

    }

    private static String[][] parseLine(String line)
            throws IncorrectMatrixSizeException, InvalidMatrixElementException {

        String[][] result = new String[MATRIX_ROWS_LENGTH][MATRIX_COLUMNS_LENGTH];

        String[] rows = line.split(ROW_SPLITTER);

        if (rows.length != MATRIX_ROWS_LENGTH) {
            throw new IncorrectMatrixSizeException(MATRIX_ROWS_LENGTH, MATRIX_COLUMNS_LENGTH);
        }

        for (int i = 0; i < rows.length; i++) {
            String[] rowElements = rows[i].split(COLUMN_SPLITTER);

            if (rowElements.length != MATRIX_COLUMNS_LENGTH) {
                throw new IncorrectMatrixSizeException(MATRIX_ROWS_LENGTH, MATRIX_COLUMNS_LENGTH);
            }

            for (int j = 0; j < rowElements.length; j++) {
                if (isInteger(rowElements[j])) {
                    result[i][j] = rowElements[j];
                } else throw new InvalidMatrixElementException(rowElements[j], i, j);
            }
        }

        return result;
    }

    private static float halfOfSumm(String[][] matrix) throws InvalidMatrixElementException {

        int summ = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (isInteger(matrix[i][j])) summ += Integer.parseInt(matrix[i][j]);
                else throw new InvalidMatrixElementException(matrix[i][j], i, j);
            }
        }

        return summ / 2f;
    }

    static boolean isInteger(String s) {
        if (s.isEmpty()) return false;
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    static <T> void printMatrix(T[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}
