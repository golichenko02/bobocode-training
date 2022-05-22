package com.bobocode.training;

public class PrintFormattedTable {
    /**
     * Given an array of strings of unspecified length, write a function that will print its contents in 5 columns.
     * The minimum distance between columns is 4 spaces.
     * <p>
     * Please take a look at the following example. Assume the array is called "input" and is passed into the function.
     * <p>
     * Output: (this should be printed to the console)
     * <p>
     * 1     2     3             x     5
     * 6     a     porosiatko    c     10
     * 11    12    13            14    15
     * 16
     */

    public static void main(String[] args) {
        String[] input = new String[]{"1", "2", "3", "x", "5", "6", "a", "porosiatko", "c", "10", "11", "12", "13", "14", "15", "16"};
        printFormatted(input);
    }

    private static void printFormatted(String[] input) {
        var n = input.length;
        var maxLengths = new int[5];
        for (int i = 0; i < n; i++) {
            int columnNumber = i % 5;
            int columnLength = input[i].length();
            if (maxLengths[columnNumber] < columnLength) {
                maxLengths[columnNumber] = columnLength;
            }
        }

        for (int i = 0; i < n; i++) {
            var elem = input[i];
            var spaceCount = maxLengths[i % 5] - elem.length() + 4;
            System.out.print(elem + " ".repeat(spaceCount));

            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

}
