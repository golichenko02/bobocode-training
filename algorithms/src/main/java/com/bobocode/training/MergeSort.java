package com.bobocode.training;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] intArr = {38, 27, 43, 3, 9, 82, 10};
        mergeSort(intArr, 0, intArr.length - 1);
        System.out.println(Arrays.toString(intArr));
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int middle = (right + left) / 2;

            mergeSort(arr, left, middle);
            mergeSort(arr, middle + 1, right);

            merge(arr, left, middle, right);
        }
    }

    private static void merge(int[] arr, int left, int middle, int right) {
        int[] leftSubArr = new int[middle - left + 1];
        int[] rightSubArr = new int[right - middle];

        System.arraycopy(arr, left, leftSubArr, 0, leftSubArr.length);
        System.arraycopy(arr, middle + 1, rightSubArr, 0, rightSubArr.length);

        int leftIndex = 0;
        int rightIndex = 0;
        int sortedArrIndex = left;
        while (leftIndex < leftSubArr.length && rightIndex < rightSubArr.length) {
            if (leftSubArr[leftIndex] <= rightSubArr[rightIndex]) {
                arr[sortedArrIndex] = leftSubArr[leftIndex++];
            } else {
                arr[sortedArrIndex] = rightSubArr[rightIndex++];
            }
            sortedArrIndex++;
        }

        System.arraycopy(leftSubArr, leftIndex, arr, sortedArrIndex, leftSubArr.length - leftIndex);
        System.arraycopy(rightSubArr, rightIndex, arr, sortedArrIndex, rightSubArr.length - rightIndex);
    }
}
