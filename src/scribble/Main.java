package scribble;

public class Main {
    public static void main(String[] args) {

        // Creates an instance of Logger
        Logger logger = Logger.getInstance();

        // settings setup
        ApplicationSettings settings = ApplicationSettings.fromArguments(args);
        assert settings != null;

        System.out.println(settings.getVerbosity());

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

        // sketch loader
        SketchBook sb = new SketchBook();
        if(settings.getSubmissionDirectoryPath() != null){
            sb.allSubmissions(settings.getSubmissionDirectoryPath());
            System.out.println("Submission folder provided");
        } else if (settings.getSingleSketchPath() != null) {
            sb.individualSubmission(settings.getSingleSketchPath());
            System.out.println("Sketch folder provided");
        } else {
            System.out.println("No submissions were stated. Terminating");
            return;
        }

        // builder sketches in sketchbook
        b.build(sb, settings.getOutputPath());

        // runner
        Runner rn = new Runner();

        // takes sketchbook and test file
        rn.runAll(sb);
        rn.run(sb,1);

    }
}
