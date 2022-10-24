package scribble.test;

import processing.core.PApplet;
import scribble.api.TestCandidate;
import scribble.log.Logger;
import scribble.sketch.Sketch;
import scribble.sketch.SketchBook;
import scribble.sketch.SketchClassLoader;
import scribble.sketch.State;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;

public class TestRunner {

    private final SketchBook sketchbook;

    public TestRunner(SketchBook sketchbook) {
        this.sketchbook = sketchbook;
    }


    public void runAll(Consumer<TestCandidate> candidateConsumer){

        Logger.info("Runner: Preparing to execute %d sketches.".formatted(sketchbook.size()));

        for (Sketch sketch : sketchbook) {
            if (sketch.getStatus() == State.BUILD_PASSED) {
                run(sketch, candidateConsumer);
                System.gc();
            } else {
                Logger.info("Runner: Skipping sketch %s/%s".formatted(sketch.getSubmissionName(), sketch.getSketchName()));
            }
        }
    }

    public void run(Sketch sk, Consumer<TestCandidate> candidateConsumer) {

        // Turn the sketch into a TestCandidate
        TestCandidate candidate = new TestCandidate();
        candidateConsumer.accept(candidate);

        // Load the sketch

        Logger.info("Loading sketch %s/%s".formatted(sk.getSubmissionName(), sk.getSketchName()));

        SketchClassLoader sketchLoader = new SketchClassLoader();

        Class<?> sketchClass;
        try {
            sketchClass = sketchLoader.loadSketch(sk);
        } catch (IOException e) {
            Logger.error("Failed to load sketch.");
            Logger.message(e.getMessage());
            return;
        }

        // Execute the sketch

        Logger.info("Executing sketch %s/%s".formatted(sk.getSubmissionName(), sk.getSketchName()));

        PApplet processingApp;
        try {
            processingApp = (PApplet) sketchClass.getConstructor().newInstance();
        }
        catch (InvocationTargetException
                 | InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException e) {
            return;
        }

        processingApp.noLoop();
        PApplet.runSketch(new String[]{sketchClass.getName()}, processingApp);

        // Do afterSetup tasks

        while (processingApp.frameCount < candidate.lifetime){
            // Do beforeDraw tasks
            processingApp.redraw();
            // Do afterDraw tasks
        }

        // Do beforeExit tasks

        processingApp.getSurface().setVisible(false);
        processingApp.dispose();
    }
}
