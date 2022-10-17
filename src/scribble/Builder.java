package scribble;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Builder {

    //compiles a pde to java
    //returns class directory

    public void buildAll(SketchBook sb){
        for (Sketch s : sb) {
            buildOne(s);
        }
    }

    public void buildOne(Sketch s) {

        // Create a directory for this sketch in the global temp folder
        Path tempPath = ApplicationSettings.getGlobalInstance().getTempPath();
        Path buildPath = tempPath.resolve("build").resolve(s.submissionName);
        boolean success = buildPath.toFile().mkdirs();
        s.setCompiledDirectory(buildPath);

        // Find processing-java
        // Construct command that is used to call processing-java and build the sketch
        StringJoiner command = new StringJoiner(" ");
        command.add("processing-java");
        command.add("--sketch=\"%s\"".formatted(s.sketchDirectory));
        command.add("--output=\"%s\"".formatted(s.compiledDirectory));
        command.add("--force");
        command.add("--build");

        Runtime rt = Runtime.getRuntime();

        Process processing;
        BufferedReader input;
        BufferedReader error;
        try {
            String commandString = command.toString();

            Logger.info("Builder: building sketch %s/%s".formatted(s.getSubmissionName(), s.getSketchName()));
            Logger.debug("Builder: executing '%s'".formatted(commandString));

            processing = rt.exec(commandString);
            input = new BufferedReader(new InputStreamReader(processing.getInputStream()));
            error = new BufferedReader(new InputStreamReader(processing.getErrorStream()));
        }
        catch (IOException e) {
            Logger.warning("Builder: Could not build sketch %s/%s: %s.".formatted(s.submissionName, s.sketchName, e.getMessage()));
            s.status = "BUILD_ERROR";
            return;
        }

        // Allow processing-java 10 seconds to build the sketch
        try {
            processing.waitFor(10, TimeUnit.SECONDS);
        }
        catch (InterruptedException ignored) {
            ;
        }
        finally {
            processing.destroy();
        }

        // Copy output from processing-java to logger
        String message;
        try {
            while ((message = input.readLine()) != null) {
                Logger.debug("processing-java: %s".formatted(message));
            }
            while ((message = error.readLine()) != null) {
                Logger.warning("processing-java: %s".formatted(message));
            }
        }
        catch (IOException ignored) {
            ;
        }

        s.status = "COMPILED";

    }
}
