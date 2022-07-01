import lombok.SneakyThrows;

public class DaemonThreadTest {
    @SneakyThrows
    public static void main(String[] args) {
        var t1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000_000; i++) {
                System.out.println("Daemon thread running: " + i);
            }
        });
        t1.setDaemon(true);
        t1.start();
        System.out.println(Thread.currentThread().getName() + " : before sleep");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " : after sleep");
        System.out.println(Thread.currentThread().getName() + " : END!");
    }
}
