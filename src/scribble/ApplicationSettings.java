package scribble;

import java.nio.file.Path;
import java.util.*;

public class ApplicationSettings {

    enum Verbosity {
        SILENT,
        QUIET,
        NORMAL,
        VERBOSE
    }

    enum OutputFormat {
        CSV,
        HTML,
        STD
    }


    private static ApplicationSettings globalInstance = null;


    private final Map <String, Object> store;


    private static final String verbosityKey = "verbosity";
    private static final String submissionDirectoryKey = "submissionDirectoryPath";
    private static final String singleSketchPathKey = "singleSketchPath";
    private static final String testFilePathKey = "testFilePath";
    private static final String outputTypeKey = "outputType";
    private static final String outputPathKey = "outputPath";


    private ApplicationSettings(Map<String, Object> settings) {
        store = settings;
    }

    /**
     * @return The global instance of ApplicationSettings if one has been created, or null otherwise
     */
    public static ApplicationSettings getGlobalInstance() {
        return globalInstance;
    }

    /**
     * @return The newly created instance of ApplicationSettings, or null if an instance already existed
     */
    public static ApplicationSettings fromMap(Map<String, Object> settings) {

        if (globalInstance != null) {
            return null;
        }

        globalInstance = new ApplicationSettings(settings);
        return globalInstance;
    }

    /**
     * Creates an ApplicationSettings object from a string or an array of strings. Intended for handling
     * command line arguments passed into the program. The newly created instance becomes the singleton instance
     * if one does not already exist.
     * @return The newly created instance, or null if an instance already existed.
     */
    public static ApplicationSettings fromArguments(String... args) {

        if (globalInstance != null) {
            return null;
        }

        List<String> argsList = Arrays.asList(args);
        Iterator<String> argsIter = argsList.iterator();

        Map<String, Object> settings = new HashMap<>();

        while (argsIter.hasNext()) {

            String arg = argsIter.next();

            switch(arg){
                case "--silent" -> settings.put(verbosityKey, Verbosity.SILENT);
                case "--quiet" ->  settings.put(verbosityKey, Verbosity.QUIET);
                case "--verbose" ->  settings.put(verbosityKey, Verbosity.VERBOSE);

                // Type: Path
                case "-t", "--testfile" -> settings.put(testFilePathKey, Path.of(argsIter.next()));

                // Type: Path
                case "-s", "--sketch" -> settings.put(singleSketchPathKey, Path.of(argsIter.next()));

                // Type: Path
                case "-S", "--sketchbook" -> settings.put(submissionDirectoryKey, Path.of(argsIter.next()));

                // Type: Path
                case "-o", "--out", "--outfile" -> settings.put(outputPathKey, Path.of(argsIter.next()));

                // Type: OutputFormat Enum
                case "-f", "--format" -> settings.put(outputTypeKey, OutputFormat.valueOf(argsIter.next()));

                default -> Logger.warning("Unrecognised argument: " + arg);
            }
        }

        globalInstance =  new ApplicationSettings(settings);
        return globalInstance;
    }

    /**
     * @return The value matching the key, as an instance of type. Or returns null if the key is null, if the value
     * stored in the map is null, or if the value stored in the map cannot be cast to type.
     */
    public <T> T getAsType(String key, Class<T> type) {

        T result;

        try {
            result = type.cast(store.get(key));
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }

        return result;
    }

    public Verbosity getVerbosity() {
        return getAsType(verbosityKey, Verbosity.class);
    }

    public Path getSubmissionDirectoryPath() {
        return getAsType(submissionDirectoryKey, Path.class);
    }

    public Path getSingleSketchPath() {
        return getAsType(singleSketchPathKey, Path.class);
    }

    public Path getTestFilePath() {
        return getAsType(testFilePathKey, Path.class);
    }

    public Path getOutputPath() { return getAsType(outputPathKey, Path.class); }

}
