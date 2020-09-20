package me.rubl.gbhomework.java_base.lesson_five;

public class Dog extends Animal {

    private static final double AVERAGE_MAX_RUN = 500;
    private static final double AVERAGE_MAX_SWIM = 10;
    private static final double AVERAGE_MAX_JUMP = 0.5;

    Dog(String name) {
        super(
                name,
                randomStat(AVERAGE_MAX_RUN),
                randomStat(AVERAGE_MAX_SWIM),
                randomStat(AVERAGE_MAX_JUMP)
        );
    }
}