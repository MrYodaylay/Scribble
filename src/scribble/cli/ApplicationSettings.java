package scribble.cli;

import scribble.log.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static scribble.cli.WellKnownSettings.*;

public class ApplicationSettings {


    private ApplicationSettings() {}


    private static Map <String, Object> store;


    /**
     * Creates a settings store from a map. Overwrites any existing settings that were stored before calling this
     * method.
     */
    public static void fromMap(Map<String, Object> settings) {
        store = settings;
    }

    /**
     * Creates a settings store from a string or an array of strings. Intended for handling command line arguments
     * passed into the program. Overwrites any existing settings that were stored before calling this method.
     */
    public static void fromArguments(String... args) {

        List<String> argsList = Arrays.asList(args);
        Iterator<String> argsIter = argsList.iterator();

        Map<String, Object> settings = new HashMap<>();

        while (argsIter.hasNext()) {

            String arg = argsIter.next();

            switch(arg){
                case "--silent" -> settings.put(verbosity, Verbosity.SILENT);
                case "--quiet" ->  settings.put(verbosity, Verbosity.QUIET);
                case "--verbose" ->  settings.put(verbosity, Verbosity.VERBOSE);

                // Type: Path
                case "-t", "--testfile" -> settings.put(testSpecificationFilePath, Path.of(argsIter.next()));

                // Type: Path
                case "-s", "--sketch" -> settings.put(singleSketchModeSubmissionPath, Path.of(argsIter.next()));

                // Type: Path
                case "-S", "--sketchbook" -> settings.put(multiSketchModeSubmissionPath, Path.of(argsIter.next()));

                // Type: Path
                case "-o", "--out", "--outfile" -> settings.put(outputFilePath, Path.of(argsIter.next()));

                // Type: OutputFormat Enum
                case "-f", "--format" -> settings.put(outputFileType, OutputFormat.valueOf(argsIter.next()));

                default -> Logger.warning("Unrecognised argument: " + arg);
            }
        }

        fromMap(settings);
    }

    /**
     * @return The value matching the key as an instance of Object.
     */
    public static Object getAsIs(String key) {
        return store.get(key);
    }

    /**
     * @return The value matching the key, as an instance of type. Or returns null if the key is null, if the value
     * stored in the map is null, or if the value stored in the map cannot be cast to type.
     */
    public static <T> T getAsType(String key, Class<T> type) {

        T result;

        try {
            result = type.cast(getAsIs(key));
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }

        return result;
    }

    public static boolean contains(String key) {
        return store.containsKey(key);
    }

    public static Path tempPath() {
        Path p = getAsType("tempDirectoryPath", Path.class);

        if (p == null) {
            try {
                p = Files.createTempDirectory("scribble");
                store.put("tempDirectoryPath", p);
            } catch (IOException e) {
                Logger.error("Could not create temporary directory.");
            }
        }

        return p;
    }

}
