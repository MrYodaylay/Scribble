package scribble;

import java.util.ArrayList;

public class SketchBook {
//implements iterable

    private final ArrayList<Sketch> sketchs = new ArrayList<>();

    //takes submissions path
    public SketchBook(String[] all){
        //take in path of all sketches
    }

    //takes individual submissions path
    public SketchBook(String indi){
        //take in path of all sketches
    }

    //implements iterable
    public Sketch getSketch (int index){

        return sketchs.get(index);

    }

}
