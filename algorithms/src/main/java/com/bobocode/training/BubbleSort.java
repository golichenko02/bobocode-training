package com.bobocode.training;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        int[] intArr = {3, 8, 6, 10, 9, 14, 12, 20, 15};
        bubbleSort(intArr);
        System.out.println(Arrays.toString(intArr));
    }

    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] arr, int j, int i) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }
}
