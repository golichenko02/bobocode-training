package com.bobocode.training;

import java.time.chrono.MinguoDate;

public class DivideAndConquerExercise {
    public static void main(String[] args) {
//        4.1 Write out the code for the earlier sum function.
//        4.2 Write a recursive function to count the number of items in a list.
//        4.3 Find the maximum number in a list.

        // 4.1
        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(sum(arr, 0));

        // 4.2
        System.out.println(countLength(arr, 0));

        // 4.3
        System.out.println(findMax(arr, 0));
    }

    private static int findMax(int[] arr, int index) {
        try {
            return Math.max(arr[index], findMax(arr, index + 1));
        } catch (Exception e) {
            return 0;
        }
    }

    private static int countLength(int[] arr, int index) {
        try {
            return arr[index] == 0 ? 0 : 1 + countLength(arr, index + 1);
        } catch (Exception e) {
            return 0;
        }
    }

    private static int sum(int[] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        return arr[index] + sum(arr, index + 1);
    }

}
