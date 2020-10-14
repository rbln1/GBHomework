package me.rubl.gbhomework.java_advanced.lesson_two.exceptions;

import com.sun.istack.internal.NotNull;

public class IncorrectMatrixSizeException extends Exception {

    String message = "Matrix size should be %dx%d";

    int rightRowLength, rightColumnLength;

    public IncorrectMatrixSizeException(@NotNull int rightRowLength, @NotNull  int rightColumnLength) {

        this.rightRowLength = rightRowLength;
        this.rightColumnLength = rightColumnLength;
    }

    @Override
    public String getMessage() {
        return String.format(message, rightRowLength, rightColumnLength);
    }
}
