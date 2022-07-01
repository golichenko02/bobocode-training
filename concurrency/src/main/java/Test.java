import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() +" : WORK DONE!");
            });
        }

        long beforeStart = System.currentTimeMillis();
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
        System.out.println(System.currentTimeMillis() - beforeStart);
    }
}
