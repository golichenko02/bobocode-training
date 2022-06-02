import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        System.out.println("STARTING MAIN THREAD");
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> loadSmth())
                .thenApply(result -> convert(result))
                .thenAccept(System.out::println);
        System.out.println("THE END");
        future.join();
    }

    @SneakyThrows
    private static Object convert(Object result) {
        System.out.println("Convert");
        Thread.sleep(2000);
        return result;
    }

    @SneakyThrows
    private static Object loadSmth() {
        System.out.println("load");
        Thread.sleep(2000);
        return Thread.currentThread();
    }
}
