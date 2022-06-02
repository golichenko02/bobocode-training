import java.util.Arrays;
import java.util.List;

public class TestParallelMergeSort {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(6, 1, 9, 3, 10);
        ParallelMergeSort<Integer> parallelMergeSort = new ParallelMergeSort<>(integers);
        System.out.println(parallelMergeSort.compute());

    }
}
