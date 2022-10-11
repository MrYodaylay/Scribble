package scribble;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Settings {
    // Verbosity
    String verbosity = "";
    // Submission folder
    String submissionFolder = "";
    // test file
    String sketchPath = "";
    String testFilePath = "";
    // output style
    String outputStyle = "";
    // output path
    String outputPath = "";

    public Settings(String[] input){
        List<String> args = Arrays.asList(input);
        Iterator<String> argsIter = args.iterator();

        while (argsIter.hasNext()) {

            String arg = argsIter.next();

            switch(arg){
                case "--silent" -> {
                    verbosity = "SILENT";
                }
                case "--quiet" -> {
                    verbosity = "QUIET";
                }
                case "--verbose" -> {
                    verbosity = "VERBOSE";
                }

                case "-t", "--testfile" -> testFilePath = argsIter.next();

                case "-s", "--sketch" -> sketchPath = argsIter.next();

                case "-S", "--sketchbook" -> submissionFolder = argsIter.next();

                case "-o", "--out", "--outfile" -> outputPath = argsIter.next();

                case "-f", "--format" -> outputStyle = argsIter.next();

                //case "-p", "--processing" -> tempProperties.put("PROCESSING_PATH", argsIter.next());

                default -> System.out.println("Unknown command in arguments: " + arg);
            }
        }


    }

    public String getTest(){

        return testFilePath;
    }
}
