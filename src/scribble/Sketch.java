package scribble;


import java.io.File;

public class Sketch {
    String sketchName = "";
    File sketchDirectory;

    //Do we need this????
    //TODO
    File sketchFile;

    File compiledDirectory;
    //status = failed to build, ...
    String status = "none";

    public Sketch(File directoryName){
        sketchDirectory = directoryName;
        sketchName = directoryName.getName();
    }

    public String getSketchName() {
        return sketchName;
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
}
