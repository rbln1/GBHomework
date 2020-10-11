package me.rubl.gbhomework.java_advanced.lesson_two.exceptions;

import com.sun.istack.internal.NotNull;

public class InvalidMatrixElementException extends Exception {

    String message = "Invalid matrix element '%s' in row %d column %d";

    Object element;
    int elementRow, elementColumn;

    public InvalidMatrixElementException(@NotNull Object element, @NotNull int elementRow, @NotNull int elementColumn) {
        this.element = element;
        this.elementRow = elementRow;
        this.elementColumn = elementColumn;
    }

    @Override
    public String getMessage() {
        return String.format(message, element, elementRow, elementColumn);
    }
}
