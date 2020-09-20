package me.rubl.gbhomework.java_base.lesson_five;

public class Cat extends Animal {

    private static final double AVERAGE_MAX_RUN = 200;
    private static final double AVERAGE_MAX_SWIM = 0;
    private static final double AVERAGE_MAX_JUMP = 2;

    Cat(String name) {
        super(
                name,
                randomStat(AVERAGE_MAX_RUN),
                randomStat(AVERAGE_MAX_SWIM),
                randomStat(AVERAGE_MAX_JUMP)
        );
    }
}