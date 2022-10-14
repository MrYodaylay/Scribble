package scribble;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;

public class SketchBook {
//implements iterable

    private final ArrayList<Sketch> sketchs = new ArrayList<>();

    //Looks at all the folders and puts in sketchs array
    public void allSubmissions(String allSubs){
        try{
            File[] all = new File(allSubs).listFiles(File::isDirectory);
            for (File f: all) {
                sketchs.add(new Sketch(f));
            }
        } catch (InvalidPathException e){
            System.out.println("Invalid submission path provided");
            return;
        }
    }

    //Looks at the one folder provided and puts in sketches array
    public void individualSubmission(String allSubs){
        File indi;
        try{
            indi = new File(allSubs);
        } catch (InvalidPathException e){
            System.out.println("Invalid submission path provided");
            return;
        }

        Sketch sk = new Sketch(indi);
        sketchs.add(sk);
    }

    //implements iterable
    public Sketch getSketch (int index){
        if(index>=sketchs.size()) return null;
        return sketchs.get(index);
    }

}
