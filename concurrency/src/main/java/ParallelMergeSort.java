import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {
    private List<T> list;

    public ParallelMergeSort(List<T> list) {
        this.list = list;
    }

    @Override
    protected List<T> compute() {
        if (list.size() < 2) {
            return list;
        }

        int middle = list.size() / 2;
        var left = new ArrayList<>(list.subList(0, middle));
        var right = new ArrayList<>(list.subList(middle, list.size()));

        var leftSorted = new ParallelMergeSort<>(left);
        var rightSorted = new ParallelMergeSort<>(right);

        leftSorted.fork();
        rightSorted.compute();
        leftSorted.join();

        merge(left, right);
        return list;
    }

    private void merge(ArrayList<T> left, ArrayList<T> right) {
        int generalIdx = 0;
        int leftIdx = 0;
        int rightIdx = 0;

        while (leftIdx < left.size() && rightIdx < right.size()) {
            T leftElem = left.get(leftIdx);
            T rightElem = right.get(rightIdx);
            if (leftElem.compareTo(rightElem) <= 0) {
                list.set(generalIdx++, leftElem);
                leftIdx++;
            } else {
                list.set(generalIdx++, rightElem);
                rightIdx++;
            }
        }

        while (leftIdx < left.size()) {
            list.set(generalIdx++, left.get(leftIdx++));
        }

        while (rightIdx < right.size()) {
            list.set(generalIdx++, left.get(rightIdx++));
        }
    }
}
