package scribble;

public class Main {
    public static void main(String[] args) {


        // settings setup
        ApplicationSettings settings = ApplicationSettings.fromArguments(args);
        assert settings != null;

        // build test file
        //if(settings.testFilePath.equals("")){
        //    System.out.println("Test file not specified. Terminating");
        //    return;
        //}

    //    System.out.println(settings.getVerbosity());

        //Builder b = new Builder();
        //b.buildTest(settings.getTestFilePath());
        //maybe change the .java to .class

        // sketch loader
    /*
        SketchBook sb = new SketchBook();
        if(settings.submissionFolder.equals("")){
            sb.allSubmissions(settings.submissionFolder);
        } else if (settings.singleSketchPath.equals("")) {
            sb.individualSubmission(settings.singleSketchPath);
        } else {
            System.out.println("No submission folder was stated. Terminating");
            return;
        }*/



        // builder sketches in sketchbook
        //b.build(sb);

        // runner
        //Runner rn = new Runner();

        // takes sketchbook and test file
    /*
        rn.runAll(sb);
        rn.run(sb,1);

     */

    }
}
