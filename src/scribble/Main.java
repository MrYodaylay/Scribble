package scribble;

public class Main {
    public static void main(String[] args) {
        // settings setup
        ApplicationSettings settings = ApplicationSettings.fromArguments(args);

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
            System.out.println("sub folder provided");
        } else if (settings.getSingleSketchPath() != null) {
            sb.individualSubmission(settings.getSingleSketchPath());
        } else {
            System.out.println("No submissions were stated. Terminating");
            return;
        }

        // builder sketches in sketchbook
        b.build(sb);

        // runner
        Runner rn = new Runner();

        // takes sketchbook and test file
        rn.runAll(sb);
        rn.run(sb,1);

    }
}
