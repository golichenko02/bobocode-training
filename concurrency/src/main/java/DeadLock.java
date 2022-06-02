import lombok.SneakyThrows;

public class DeadLock {
    private static String lock1 = "lock1";
    private static String lock2 = "lock2";

    @SneakyThrows
    public static void main(String[] args) {
        Runnable r1 = () -> {
            synchronized (lock1) {
                System.out.println("Thread-1: lock1");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println("Thread-1: lock2");
                }
            }
        };

        Runnable r2 = () -> {
            synchronized (lock2) {
                System.out.println("Thread-2: lock2");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock1) {
                    System.out.println("Thread-2: lock1");
                }
            }
        };

        var t1 = new Thread(r1);
        var t2 = new Thread(r2);

        var t3 = new Thread(() -> {
            while (true) {
                System.out.println(t1.getState());
                System.out.println(t2.getState());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
    }
}
