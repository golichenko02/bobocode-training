package com.bobocode.training;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {

        int[] intArr = {3, 8, 6, 10, 9, 14, 12, 20, 15};
        quickSort(intArr, 0, intArr.length - 1);
        System.out.println(Arrays.toString(intArr));
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    //            5, 3, 9, 7, 8    pivot = 8;
    // 1)   5, 3, 9, 7, 8  --> latestLowElementIndex = 0, j = 0
    // 2)   5, 3, 9, 7, 8  --> latestLowElementIndex = 1, j = 1
    // 3)   5, 3, 9, 7, 8  --> latestLowElementIndex = 1, j = 2
    // 4)   5, 3, 7, 9, 8  --> latestLowElementIndex = 2, j = 3
    //

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int latestLowElementIndex = low - 1;
        for (int j = latestLowElementIndex + 1; j < high - 1; j++) {
            if (arr[j] < pivot) {
                latestLowElementIndex++;
                swap(arr, latestLowElementIndex, j);
            }
        }
        swap(arr, latestLowElementIndex + 1, high);
        return latestLowElementIndex + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
