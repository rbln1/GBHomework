package me.rubl.gbhomework.java_base.lesson_five;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Animal {

    private String name;
    private final double maxRun;
    private final double maxSwim;
    private final double maxJump;

    public Animal(String name, double maxRun, double maxSwim, double maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxSwim = maxSwim;
        this.maxJump = maxJump;
    }

    public String getName() {
        return name;
    }

    public double getMaxRun() {
        return maxRun;
    }

    public double getMaxSwim() {
        return maxSwim;
    }

    public double getMaxJump() {
        return maxJump;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullInfo() {
        StringBuilder sb = new StringBuilder();

        if (this instanceof Cat) sb.append("Cat - ");
        else if (this instanceof Dog) sb.append("Dog - ");

        sb.append(getName()).append(". ")
                .append(getMaxRun() > 0 ? "Can run " + getMaxRun() + "m, " : "Cant run, ")
                .append(getMaxSwim() > 0 ? "can swim " + getMaxSwim() + "m, " : "cant swim, ")
                .append(getMaxJump() > 0 ? "can jump " + getMaxJump() + "m. " : "cant jump.");
        return sb.toString();
    }

    boolean run(double distance) {
        return distance > 0 && distance <= maxRun;
    }

    boolean swim(double distance) {
        return distance > 0 && distance <= maxSwim;
    }

    boolean jump(double height) {
        return height > 0 && height <= maxJump;
    }

    static double randomStat(double average_value) {

        if (average_value == 0) return 0;

        double percentage = average_value * 0.3;
        double max = average_value + percentage;
        double min = average_value - percentage;

        double stat = (Math.random() * ((max - min) + 1)) + min;
        BigDecimal roundedStat = new BigDecimal((Double.toString(stat)));
        roundedStat = roundedStat.setScale(2, RoundingMode.HALF_UP);
        return roundedStat.doubleValue();
    }
}
