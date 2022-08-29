import lombok.SneakyThrows;

public class DeadLock2 {

    private static String lock1 = "lock1";
    private static String lock2 = "lock2";

    @SneakyThrows
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread-1: lock1");
            synchronized (lock1) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
                synchronized (lock2) {
                    System.out.println("Thread-1: lock2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            System.out.println("Thread-2: lock2");
            synchronized (lock2) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
                synchronized (lock1) {
                    System.out.println("Thread-1: lock1");
                }
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(t1.getState());
                System.out.println(t2.getState());
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
    }
}
