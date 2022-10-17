package scribble;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) {

        // Creates an instance of Logger
        Logger logger = Logger.getInstance();

        // settings setup
        ApplicationSettings settings = ApplicationSettings.fromArguments(args);
        assert settings != null;

        Builder b = new Builder();

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
        SketchBook sb = null;

        if(settings.getSubmissionDirectoryPath() != null) {
            Logger.debug("Main: Running in sketchbook mode.");
            sb = new SketchBook(settings.getSubmissionDirectoryPath());
        }
        else if (settings.getSingleSketchPath() != null) {
            Logger.debug("Main: Running in single sketch mode.");
            sb = new SketchBook(settings.getSingleSketchPath());
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
            Files.walkFileTree(settings.getTempPath(), new SimpleFileVisitor<Path>() {
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

    }
}
