import lombok.SneakyThrows;

import java.util.Random;

public class InterruptedExample {

    @SneakyThrows
    public static void main(String[] args) {
        var t = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 1_000_000_000; i++) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("I was interrupted");
//                    break;
                }
                System.out.println(Math.sin(random.nextInt()));
            }
        });


        System.out.println("Thread started!");
        long before = System.currentTimeMillis();
        t.start();
        Thread.sleep(3000);
        t.interrupt();
//        Thread.sleep(5000);
//        t.join();
        System.out.println("Thread has finished!");
        System.out.println("Execution took: " + (System.currentTimeMillis() - before) + " ms");
    }
}
