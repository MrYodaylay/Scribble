package scribble;


import java.io.File;

public class Sketch {
    String submissionName = "";
    File sketchDirectory;
    String sketchName = "";

    //Do we need this????
    //TODO
    File sketchFile;

    File compiledDirectory;
    //status = failed to build, ...
    String status = "none";

    public Sketch(File directoryName){
        sketchDirectory = directoryName;
        submissionName = directoryName.getName();
        //lists the directories, which should only be one, then gets the name of that.
        sketchName = directoryName.listFiles(File::isDirectory)[0].getName();
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

    public File getCompiledDirectory() {
        return compiledDirectory;
    }

    public void setCompiledDirectory(File compDirect){
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
