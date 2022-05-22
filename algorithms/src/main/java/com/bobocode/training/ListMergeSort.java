package com.bobocode.training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListMergeSort {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(38, 27, 43, 3, 9, 82, 10);
        mergeSort(integers);
        System.out.println(integers);
    }

    private static <T extends Comparable<? super T>> void mergeSort(List<T> list) {
        if (list.size() < 2) {
            return;
        }

        var middle = (list.size() / 2);
        var leftSubList = new ArrayList<>(list.subList(0, middle));
        var rightSubList = new ArrayList<>(list.subList(middle, list.size()));
        mergeSort(leftSubList);
        mergeSort(rightSubList);
        merge(list, leftSubList, rightSubList);
    }

    private static <T extends Comparable<? super T>> void merge(List<T> generalList, List<T> leftSubList, List<T> rightSubList) {
        int leftIndex = 0;
        int rightIndex = 0;
        int generalIndex = 0;

        while (leftIndex < leftSubList.size() && rightIndex < rightSubList.size()) {
            if (leftSubList.get(leftIndex).compareTo(rightSubList.get(rightIndex)) <= 0) {
                generalList.set(generalIndex++, leftSubList.get(leftIndex++));
            } else {
                generalList.set(generalIndex++, rightSubList.get(rightIndex++));
            }
        }

        while (leftIndex < leftSubList.size()) {
            generalList.set(generalIndex++, leftSubList.get(leftIndex++));
        }

        while (rightIndex < rightSubList.size()) {
            generalList.set(generalIndex++, rightSubList.get(rightIndex++));
        }
    }
}
