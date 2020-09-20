package me.rubl.gbhomework.java_base.lesson_five;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class LessonFive {

    /*
        1.Создать классы Собака и Кот с наследованием от класса Животное.
        2.Животные могут выполнять действия: бежать, плыть, перепрыгивать препятствие.
            В качестве параметра каждому методу передается величина,
            означающая или длину препятствия (для бега и плавания), или высоту (для прыжков).
        3.У каждого животного есть ограничения на действия
            (
                бег: кот 200 м., собака 500 м.;
                прыжок: кот 2 м., собака 0.5 м.;
                плавание: кот не умеет плавать, собака 10 м.
            ).
        4.При попытке животного выполнить одно из этих действий, оно должно сообщить результат.
            (Например, dog1.run(150); -> результат: 'Пёсик пробежал!')
        5*. Добавить животным разброс в ограничениях.
            То есть у одной собаки ограничение на бег может быть 400 м., у другой 600 м.
    */

    public static void main(String[] args) {

        ArrayList<Animal> animals = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) animals.add(new Cat("Cat#" + i));
            else animals.add(new Dog("Dog#" + i));
        }

        getFullInfoAboutParticipants(animals);

        race(animals, randomValue(500));
        swim(animals, randomValue(20));
        jump(animals, randomValue(2));
    }

    static void getFullInfoAboutParticipants(ArrayList<Animal> participants) {

        System.out.println("\t----- Information about participants -----");

        for (Animal participant : participants) {
            System.out.println(participant.getFullInfo());
        }
    }

    static void race(ArrayList<Animal> participants, double distance) {

        System.out.printf("\n\t----- Race of %.2fm -----%n", distance);

        for (Animal participant : participants) {
            boolean actionResult = participant.run(distance);

            if (participant instanceof Dog) System.out.print("Dog ");
            else if (participant instanceof Cat) System.out.print("Cat ");
            System.out.println(
                    participant.getName() + (actionResult ? " successfully ran." : " could not run.")
            );
        }
    }

    static void swim(ArrayList<Animal> participants, double distance) {

        System.out.printf("\n\t----- Swim of %.2fm -----%n", distance);

        for (Animal participant : participants) {
            boolean actionResult = participant.swim(distance);

            if (participant instanceof Dog) System.out.print("Dog ");
            else if (participant instanceof Cat) System.out.print("Cat ");
            System.out.println(
                    participant.getName() + (actionResult ? " swam successfully." : " could not swim.")
            );
        }
    }

    static void jump(ArrayList<Animal> participants, double height) {

        System.out.printf("\n\t----- Jump of %.2fm -----%n", height);

        for (Animal participant : participants) {
            boolean actionResult = participant.jump(height);

            if (participant instanceof Dog) System.out.print("Dog ");
            else if (participant instanceof Cat) System.out.print("Cat ");
            System.out.println(
                    participant.getName() + (actionResult ? " successfully jumped." : " did not jump.")
            );
        }

    }

    static double randomValue(double max) {
        double distance = (Math.random() * (max + 1));
        BigDecimal roundedDist = new BigDecimal((Double.toString(distance)));
        roundedDist = roundedDist.setScale(2, RoundingMode.HALF_UP);
        return roundedDist.doubleValue();
    }
}
