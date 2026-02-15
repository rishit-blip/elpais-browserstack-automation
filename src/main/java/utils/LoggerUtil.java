package utils;

public class LoggerUtil {

    public static void log(String message) {
        String thread = Thread.currentThread().getName();
        System.out.println("[" + thread + "] " + message);
    }
}
