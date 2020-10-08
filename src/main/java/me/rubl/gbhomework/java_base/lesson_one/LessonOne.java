package me.rubl.gbhomework.java_base.lesson_one;

public class LessonOne {

    /*
        + 1. Написать метод вычисляющий выражение a * (b + (c / d))
            и возвращающий результат с плавающей точкой,
            где a, b, c, d – целочисленные входные параметры этого метода;
        + 2. Написать метод, принимающий на вход два целых числа,
            и проверяющий что их сумма лежит в пределах от 10 до 20(включительно),
            если да – вернуть true, в противном случае – false;
        + 3. Написать метод, которому в качестве параметра передается целое число,
            метод должен проверить положительное ли число передали, или отрицательное.
            Замечание: ноль считаем положительным числом.
            Результат работы метода вывести в консоль
        +4. Написать метод, которому в качестве параметра передается строка,
            обозначающая имя, метод должен вернуть приветственное сообщение
            «Привет, переданное_имя!»; Вывести приветствие в консоль.
        +5. * Написать метод, который определяет является ли год високосным.
            Каждый 4-й год является високосным, кроме каждого 100-го, при этом каждый 400-й – високосный.
            Для проверки работы вывести результаты работы метода в консоль
    */

    public static void main(String[] args) {

        // Task 1
        System.out.println(taskOne(3, 2, 1, 2));

        // Task 2
        System.out.println(taskTwo(5, 8));

        // Task 3
        taskThree(10);

        // Task 4
        taskFour("Andrey");

        // Task 5*
        taskFive(1600);
    }

    static float taskOne(int a, int b, int c, int d) {

        return a * (b + (c / (d * 1f)));
    }

    static boolean taskTwo(int a, int b) {

        int summary = a + b;

        return summary >= 10 && summary <= 20;
    }

    static void taskThree(int a) {

        System.out.println(a >= 0 ? "Digit is positive" : "Digit is negative");
    }

    static void taskFour(String name) {

        System.out.printf("Привет, %s!\n", name);
    }

    static void taskFive(int year) {

        if ((year % 4 == 0 && year % 100 != 0) || (year % 100 == 0 && year % 400 == 0)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}