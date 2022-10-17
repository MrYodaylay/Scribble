package scribble;


import java.io.File;
import java.nio.file.Path;

public class Sketch {


    // STATIC VARIABLES & METHODS

    /**
     * Returns true if and only if the passed file is a valid Processing sketch. A valid processing Sketch is a
     * directory containing one or more files with the .pde file extension. In all other cases, returns false. The
     * comparison of file extensions is case-insensitive (i.e. ".PDE" == ".pde").
     */
    public static boolean isValidSketch(Path testDirectoryPath) {
        // FUTURE Cache results of validity test to improve performance

        File testDirectory = testDirectoryPath.toFile();

        // Try to fail fast with quick sanity checks
        if (!testDirectory.isDirectory()) {
            return false;
        }
        if (!testDirectory.canRead()) {
            return false;
        }

        // Find if there is a processing file in this directory
        File[] pdeFiles = testDirectory.listFiles(p->p.toString().toLowerCase().endsWith(".pde"));
        return pdeFiles != null && pdeFiles.length != 0;

    }


    // INSTANCE VARIABLES & METHODS

    String submissionName;
    File sketchDirectory;
    String sketchName;

    //Do we need this????
    //TODO
    File sketchFile;

    Path compiledDirectory;
    //status = failed to build, ...
    String status = "none";


    public Sketch(Path sketchPath) {
        sketchDirectory = sketchPath.toFile();
        submissionName = String.valueOf(sketchPath.getParent().getFileName());
        sketchName = sketchDirectory.getName();
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public File getSketchDirectory() {
        return sketchDirectory;
    }

    public File getSketchFile() {
        return sketchFile;
    }

    public Path getCompiledDirectory() {
        return compiledDirectory;
    }

    public void setCompiledDirectory(Path compDirect){
        this.compiledDirectory = compDirect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSketchName() {
        return sketchName;
    }

}
