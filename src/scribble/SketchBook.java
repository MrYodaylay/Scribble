package scribble;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class SketchBook implements Iterable<Sketch> {

    private final List<Sketch> sketches;

    public SketchBook(Path submissionsPath) {

        // Filter the given directory only to subdirectories which contain valid sketches
        List<Path> submissionSubdirectories = null;
        try (Stream<Path> walk = Files.walk(submissionsPath, 5)) {
            submissionSubdirectories = walk.filter(Sketch::isValidSketch).toList();
        } catch (IOException e) {
            Logger.warning("IO Exception occurred while walking %s".formatted(submissionsPath));
        }

        if (submissionSubdirectories == null || submissionSubdirectories.isEmpty()) {
            sketches = new ArrayList<>(0);
            return;
        }

        sketches = new ArrayList<>(submissionSubdirectories.size());
        sketches.addAll(submissionSubdirectories.stream().map(Sketch::new).toList());
    }

    public Sketch getSketch (int index){
        if(index>= sketches.size()) return null;
        return sketches.get(index);
    }

    @Override
    public Iterator<Sketch> iterator() {
        return sketches.iterator();
    }

    public int size() {
        return sketches.size();
    }
}
