public class ThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        stringThreadLocal.set("main");


        var t1 = new Thread(() -> {
            stringThreadLocal.set("t1");
            System.out.println(stringThreadLocal.get());
        });
        t1.start();
        System.out.println(stringThreadLocal.get());
    }
}
