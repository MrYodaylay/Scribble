package scribble;

import scribble.api.Scribble;
import scribble.cli.ApplicationSettings;
import scribble.log.Logger;
import scribble.sketch.SketchBook;
import scribble.sketch.SketchBuilder;
import scribble.test.SpecificationBuilder;
import scribble.test.TestRunner;
import scribble.test.TestPlan;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static scribble.cli.WellKnownSettings.testSpecificationFilePath;

public class Main {
    public static void main(String[] args) {


        // Handle command line arguments
        ApplicationSettings.fromArguments(args);


        // Load test specification file
        TestPlan testPlan = null;
        if (ApplicationSettings.contains(testSpecificationFilePath)) {
            SpecificationBuilder testBuilder = new SpecificationBuilder();
            Path testSpecJavaPath = ApplicationSettings.getAsType(testSpecificationFilePath, Path.class);

            if (testSpecJavaPath == null) {
                Logger.fatal("Test specification path was supplied but it was not a valid path. Exiting.");
                System.exit(-1);
            }

            Path testSpecClassPath = testBuilder.compile(testSpecJavaPath);

            if (testSpecClassPath == null) {
                Logger.fatal("The test specification file was supplied but could not be loaded. Exiting.");
                System.exit(-1);
            }

            Class<TestPlan> testPlanClass = testBuilder.load(testSpecClassPath);
            try {
                testPlan = testPlanClass.getConstructor().newInstance();
            } catch (InstantiationException
                     | IllegalAccessException
                     | InvocationTargetException
                     | NoSuchMethodException e) {
                Logger.fatal("Test plan class was supplied but could not be instantiated. Exiting.");
            }
        } else {
            Logger.warning("A test specification file was not supplied. Sketches will be executed but will not be tested.");
        }


        // Load sketches into Sketchbook
        SketchBook sketchbook = null;

        if(ApplicationSettings.getAsType("submissionDirectoryPath", Path.class) != null) {
            Logger.debug("Main: Running in sketchbook mode.");
            sketchbook = new SketchBook(ApplicationSettings.getAsType("submissionDirectoryPath", Path.class));
        }
        else if (ApplicationSettings.getAsType("singleSketchPath", Path.class) != null) {
            Logger.debug("Main: Running in single sketch mode.");
            sketchbook = new SketchBook(ApplicationSettings.getAsType("singleSketchPath", Path.class));
        }
        else {
            Logger.fatal("No sketch(es) provided. Must provide either --sketch or --sketchbook. Exiting.");
            System.exit(-10);
        }


        // Build all sketches
        SketchBuilder sketchBuilder = new SketchBuilder();
        sketchBuilder.buildAll(sketchbook);


        // Create the testManager, which is responsible for actually executing tests
        TestRunner testRunner = new TestRunner(sketchbook);

        // The scribble object serves as the entry point for the API and coordinates all test runs
        Scribble scribble = new Scribble(testRunner, testPlan);
        scribble.run();

        // TEMPORARY:
        sketchbook.printResults();

        // Clean up temp directory before exit
        try {
            Files.walkFileTree(ApplicationSettings.tempPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.deleteIfExists(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.deleteIfExists(dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException ignored) {
            Logger.warning("Failed to clean up temp directory.");
        }

        System.exit(0);
    }
}
