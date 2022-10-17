package scribble;

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

        /*
        // runner
        Runner rn = new Runner();

        // takes sketchbook and test file
        rn.runAll(sb);

        //rn.run(sb,1);
        */
    }
}
