package com.bobocode.training;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        int[] intArr = {3, 8, 6, 10, 9, 14, 12, 20, 15};
        insertionSort(intArr);
        System.out.println(Arrays.toString(intArr));
    }

    public static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int current = arr[i]; // select the first element in the unsorted part of array
            int j = i - 1; // set the latest element in the sorted part of array
            while (j >= 0 && arr[j] > current) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = current; // because arr[j] is lower than current
        }
    }
}
