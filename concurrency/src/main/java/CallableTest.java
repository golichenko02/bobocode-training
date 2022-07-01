import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> submit = executorService.submit(() -> new Random().nextInt(10));
        executorService.shutdown();
        System.out.println(submit.get());
    }
}
