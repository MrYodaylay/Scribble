package scribble;

import scribble.cli.ApplicationSettings;
import scribble.log.Logger;
import scribble.test.SpecificationBuilder;
import scribble.test.TestPlan;

import java.nio.file.Path;

import static scribble.cli.WellKnownSettings.testSpecificationFilePath;

public class Main {
    public static void main(String[] args) {

        // Handle command line arguments
        ApplicationSettings.fromArguments(args);

        // Load test specification file
        SpecificationBuilder sb = new SpecificationBuilder();
        Path testSpecJavaPath = ApplicationSettings.getAsType(testSpecificationFilePath, Path.class);

        if (testSpecJavaPath == null) {
            Logger.fatal("Test specification path is either missing or not a valid path. Exiting.");
            System.exit(-1);
        }

        Path testSpecClassPath = sb.compile(testSpecJavaPath);

        if (testSpecClassPath == null) {
            Logger.fatal("The test specification file could not be loaded. Exiting.");
            System.exit(-1);
        }

        Class<TestPlan> tp = sb.load(testSpecClassPath);


        //Builder b = new Builder();

        // build test file
        /*if(settings.testFilePath.equals("")){
            System.out.println("Test file not specified. Terminating");
            return;
        } else {

            b.buildTest(settings.getTestFilePath());
        }
         */

        //maybe change the .java to .class

        // Create sketchbook
        /*
        SketchBook sb = null;

        if(ApplicationSettings.getAsType("submissionDirectoryPath", Path.class) != null) {
            Logger.debug("Main: Running in sketchbook mode.");
            sb = new SketchBook(ApplicationSettings.getAsType("submissionDirectoryPath", Path.class));
        }
        else if (ApplicationSettings.getAsType("singleSketchPath", Path.class) != null) {
            Logger.debug("Main: Running in single sketch mode.");
            sb = new SketchBook(ApplicationSettings.getAsType("singleSketchPath", Path.class));
        }
        else {
            Logger.fatal("No sketch(es) provided. Must provide either --sketch or --sketchbook. Exiting.");
            System.exit(-10);
        }

        // builder sketches in sketchbook
        b.buildAll(sb);


        // runner
        Runner rn = new Runner();

        // takes sketchbook and test file
        rn.runAll(sb);

        //rn.run(sb,1);

        // Clean up temp directory on exit.
        // Maybe move this outside of main?
        try {
            Files.walkFileTree(settings.tempPath(), new SimpleFileVisitor<Path>() {
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

        System.exit(-1);

         */
    }
}
