import lombok.SneakyThrows;

import java.util.stream.IntStream;

public class InconsistentDataIssue {

    private static volatile long counter;

    @SneakyThrows
    public static void main(String[] args) {
        Runnable incrementLogic = () -> {
            for (long i = 0; i < 500_000; i++) {
                /*synchronized (InconsistentDataIssue.class) {*/  // synchronized resolve the problem of data inconsistency by performing the block of code sequentially by each thread
                    counter++;
                /*}*/
            }
        };

        var t1 = new Thread(incrementLogic);
        var t2 = new Thread(incrementLogic);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Counter: " + counter);
    }


}
