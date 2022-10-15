package scribble;

public class Logger {

    enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        FATAL
    }

    private static Logger instance = null;

    private Logger() {

    }

    public static Logger getInstance() {
        if (instance != null) {
            return instance;
        }

        instance = new Logger();
        return instance;
    }

    public static void debug(String message) {
        getInstance().log(Level.DEBUG, message);
    }

    public static void info(String message) {
        getInstance().log(Level.INFO, message);
    }

    public static void warning(String message) {
        getInstance().log(Level.WARNING, message);
    }

    public static void error(String message) {
        getInstance().log(Level.ERROR, message);
    }

    public static void fatal(String message) {
        getInstance().log(Level.FATAL, message);
    }

    private void log(Logger.Level level, String message) {
        System.out.printf("[%s] %s\n", level.toString(), message);
        // TODO Respect application verbosity settings
    }

}
