package scribble;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
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

    /*
    private String compile(String command){
        try{
            Runtime rt = Runtime.getRuntime();
            Process compilation = rt.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(compilation.getInputStream()));
            String s;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
            BufferedReader er = new BufferedReader(new InputStreamReader(compilation.getErrorStream()));
            String ser;
            while ((ser = in.readLine()) != null) {
                System.out.println(ser);
            }

            try {
                compilation.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
                ;
            }
            compilation.destroy();

        } catch (IOException e){
            System.out.println("Unable to compile " + command);
            return "ERROR";
        }
        return "COMPILED";
    }

    public void buildTest(String testPath){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //TODO
        int compilationSuccess = compiler.run(null, null, null, testPath);

        if(compilationSuccess != 0){
            System.out.printf("shit broke in builder");
        }
    }
    */
}
