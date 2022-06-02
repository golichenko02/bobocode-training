import lombok.SneakyThrows;

public class OutdatedDataIssue {
    private static String message = "Hi! Concurrency is not so scary!";
    private static /*volatile*/ boolean isReady = false; // volatile - tells the compiler that the value of the variable must never be cached

    @SneakyThrows
    public static void main(String[] args) {
        var printedThread = new Thread(() -> {
            while (!isReady){}
            System.out.println(message);
        });
        printedThread.start();
        System.out.println("Before 1st sleep");
        Thread.sleep(500);

        System.out.println("Before 2nd sleep: change isReady = true and message");
        message = "New message!";
        isReady = true;

        printedThread.join();
        System.out.println("After join");
    }
}
