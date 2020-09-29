package me.rubl.gbhomework.java_base.lesson_two;

import java.util.Arrays;

public class LessonTwo {

    /*
        + 1. Задать целочисленный массив, состоящий из элементов 0 и 1.
            Например: [ 1, 1, 0, 0, 1, 0, 1, 1, 0, 0 ].
            Написать метод, заменяющий в принятом массиве 0 на 1, 1 на 0;
        + 2. Задать пустой целочисленный массив размером 8.
            Написать метод, который помощью цикла заполнит его значениями 1 4 7 10 13 16 19 22;
        + 3. Задать массив [ 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 ]
            Написать метод, принимающий на вход массив и умножающий числа меньше 6 на 2;
        + 4. Задать одномерный массив. Написать методы поиска в нём минимального и максимального элемента;
        + 5. * Создать квадратный целочисленный массив (количество строк и столбцов одинаковое),
            заполнить его диагональные элементы единицами, используя цикл(ы);
        + 6. ** Написать метод, в который передается не пустой одномерный целочисленный массив,
            метод должен вернуть true если в массиве есть место, в котором сумма левой и правой части массива равны.
            Примеры:
                checkBalance([1, 1, 1, || 2, 1]) → true,
                checkBalance ([2, 1, 1, 2, 1]) → false,
                checkBalance ([10, || 1, 2, 3, 4]) → true.
            Абстрактная граница показана символами ||, эти символы в массив не входят.
        + 7. *** Написать метод, которому на вход подаётся одномерный массив и число n
            (может быть положительным, или отрицательным),
            при этом метод должен циклически сместить все элементы массива на n позиций.
        + 8. **** Не пользоваться вспомогательным массивом при решении задачи 7.
    */

    public static void main(String[] args) {

        // Task 1
        System.out.println("[Task 1]\n" + Arrays.toString(taskOne()));

        // Task 2
        System.out.println("\n[Task 2]\n" + Arrays.toString(taskTwo()));

        // Task 3
        int[] arrayTaskThree = { 1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1 };
        System.out.println("\n[Task 3]\n" + Arrays.toString(taskThree(arrayTaskThree)));

        // Task 4
        int[] arrayTaskFour = { 6, 5, 3, 14, 11, 4, 5, 2, 4, 8, 9, 7 };
        System.out.printf("\n[Task 4]\nmin = %d, max = %d\n",
                taskFourFindMin(arrayTaskFour),
                taskFourFindMax(arrayTaskFour));

        // Task 5
        System.out.println("\n[Task 5]");
        printArray(taskFive());

        // Task 6
        System.out.println("\n[Task 6]\n" + taskSix(new int[] { 1,2,3,4,5 }));

        // Task 7
        System.out.println("\n[Task 7]\n");
        int[] arrayTaskSeven = {1,2,3,4,5};
        System.out.println("before shift:");
        printArray(arrayTaskSeven);
        taskSeven(arrayTaskSeven, -2);
        System.out.println("after shift:");
        printArray(arrayTaskSeven);
    }

    static int[] taskOne() {

        int[] array = {
                1, 1, 0, 0, 1, 0, 1, 1, 0, 0
        };

        for (int i = 0, length = array.length; i < length; i++) {
            if (array[i] == 1) array[i] = 0;
            else array[i] = 1;
        }

        return array;
    }

    static int[] taskTwo() {

        int[] array = new int[8];

        for (int i = 0, length = array.length; i < length; i++) {
            array[i] = i * 3 + 1;
        }

        return array;
    }

    static int[] taskThree(int[] array) {

        for (int i = 0, length = array.length; i < length; i++) {
            if (array[i] < 6) array[i] *= 2;
        }

        return array;
    }

    static int taskFourFindMin(int[] array) {

        int min = array[0];

        for (int i = 0, length = array.length; i < length; i++) {
            if (min > array[i]) min = array[i];
        }

        return min;
    }

    static int taskFourFindMax(int[] array) {

        int max = array[0];

        for (int i = 0, length = array.length; i < length; i++) {
            if (max < array[i]) max = array[i];
        }

        return max;
    }

    static int[][] taskFive() {

        int arrSize = 6;
        int[][] array = new int[arrSize][arrSize];

        for (int i = 0; i < arrSize; i++) {
            for (int j = 0; j < arrSize; j++) {
                if (i==j) array[i][j] = 1;

            }
            array[i][arrSize-1-i] = 1;
        }

        return array;
    }

    static boolean taskSix(int[] array) {

        for (int i = 0, length = array.length; i < length; i++) {
            int left = 0;
            for (int n = 0; n <= i; n++) {
                left += array[n];
            }

            int right = 0;
            for (int m = array.length-1; m > i; m--){
                right += array[m];
            }

            if (left == right) return true;
        }

        return false;
    }

    static int[] taskSeven(int[] array, int n) {

        boolean direction = n < 0;
        if (direction) n = -n;

        n %= array.length;

        for (int i = 0; i < n; i++) {
            int tmp = (direction) ? array[0] : array[array.length - 1];
            if (direction) {
                System.arraycopy(array, 1, array, 0, array.length - 1);
                array[array.length - 1] = tmp;
            } else {
                System.arraycopy(array, 0, array, 1, array.length - 1);
                array[0] = tmp;
            }
        }

        return array;
    }

    static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println();
    }

    static void printArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
