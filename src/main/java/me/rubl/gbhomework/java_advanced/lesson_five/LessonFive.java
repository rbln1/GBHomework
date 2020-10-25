package me.rubl.gbhomework.java_advanced.lesson_five;

import java.util.Arrays;

public class LessonFive {

    private static final int SIZE = 10_000_000;
    private static final int H = SIZE / 2;

    public static void main(String[] args) {
        methodOne();
        methodTwo();
    }

    private static void methodOne() {
        float[] arr = getBaseArray();

        long timeOfStart = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
        }

        long resultTime = System.currentTimeMillis() - timeOfStart;

        System.out.println(String.format("methodOne time: %d ms.", resultTime));
    }

    private static void methodTwo() {

        try {
            float[] arr = getBaseArray();

            long timeOfStart = System.currentTimeMillis();

            float[] partOne = new float[H];
            float[] partTwo = new float[H];

            System.arraycopy(arr, 0, partOne, 0, H);
            System.arraycopy(arr, H, partTwo, 0, H);

            Thread threadOne = new Thread(() -> {
                for (int i = 0; i < partOne.length; i++) {
                    partOne[i] = (float)(partOne[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
                }
            });
            Thread threadTwo = new Thread(() -> {
                for (int i = 0; i < partTwo.length; i++) {
                    partTwo[i] = (float)(partTwo[i] * Math.sin(0.2f + (H + i) / 5f) * Math.cos(0.2f + (H + i) / 5f) * Math.cos(0.4f + (H + i) / 2f));
                }
            });

            threadOne.start();
            threadTwo.start();

            threadOne.join();
            threadTwo.join();

            System.arraycopy(partOne, 0, arr, 0, H);
            System.arraycopy(partTwo, 0, arr, H, H);

            long resultTime = System.currentTimeMillis() - timeOfStart;

            System.out.println(String.format("methodTwo time: %d ms.", resultTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static float[] getBaseArray() {
        float[] result = new float[SIZE];
        Arrays.fill(result, 1);
        return result;    }
}
