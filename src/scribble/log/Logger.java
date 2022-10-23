package scribble.log;

import scribble.cli.ApplicationSettings;
import scribble.cli.Verbosity;

public class Logger {


    private static Level lastLevel = Level.INFO;

    public static void debug(String message) {
        log(Level.DEBUG, message);
    }

    public static void info(String message) {
        log(Level.INFO, message);
    }

    public static void warning(String message) {
        log(Level.WARNING, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }

    public static void fatal(String message) {
        log(Level.FATAL, message);
    }

    private static void log(Level level, String message) {
        lastLevel = level;

        Verbosity vb = ApplicationSettings.getAsType("verbosity", Verbosity.class);
        if (vb == null) {
            return;
        }

        switch (vb) {
            case SILENT -> {}
            case QUIET -> {
                if (level == Level.WARNING
                        || level == Level.ERROR
                        || level == Level.FATAL) {
                    System.out.printf("[%s] %s\n", level, message);
                }
            }
            case NORMAL -> {
                if (level == Level.INFO
                        || level == Level.WARNING
                        || level == Level.ERROR
                        || level == Level.FATAL) {
                    System.out.printf("[%s] %s\n", level, message);
                }
            }
            case VERBOSE -> {
                System.out.printf("[%s] %s\n", level, message);
            }
        }
    }

    public static void message(String message) {

        Verbosity vb = ApplicationSettings.getAsType("verbosity", Verbosity.class);
        if (vb == null) {
            return;
        }

        switch (vb) {
            case SILENT -> {
            }
            case QUIET -> {
                if (lastLevel == Level.WARNING
                        || lastLevel == Level.ERROR
                        || lastLevel == Level.FATAL) {
                    System.out.println(message);
                }
            }
            case NORMAL -> {
                if (lastLevel == Level.INFO
                        || lastLevel == Level.WARNING
                        || lastLevel == Level.ERROR
                        || lastLevel == Level.FATAL) {
                    System.out.println(message);
                }
            }
            case VERBOSE -> {
                System.out.println(message);
            }
        }
    }


    private Logger() {}


}
