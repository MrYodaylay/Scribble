package scribble.test;

import processing.core.PApplet;
import scribble.api.TestCandidate;
import scribble.api.TestKey;
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

        // Turn the sketch into a TestCandidate
        TestCandidate candidate = new TestCandidate();
        candidateConsumer.accept(candidate);
        candidate.processingApp = processingApp;

        // Run the sketch
        processingApp.noLoop();
        PApplet.runSketch(new String[]{sketchClass.getName()}, processingApp);

        if (!candidate.visible) {
            processingApp.getSurface().setVisible(false);
        }

        candidate.executeAll(new TestKey(0, TestKey.Event.AFTER_SETUP));

        while (processingApp.frameCount < candidate.lifetime){
            candidate.executeAll(new TestKey(processingApp.frameCount, TestKey.Event.BEFORE_DRAW));

            processingApp.redraw();

            candidate.executeAll(new TestKey(processingApp.frameCount, TestKey.Event.AFTER_DRAW));
        }

        candidate.executeAll(new TestKey(candidate.lifetime, TestKey.Event.BEFORE_EXIT));

        processingApp.getSurface().setVisible(false);
        processingApp.dispose();

        // Return results to sketch object
        sk.saveResults(candidate.results());
        sk.setStatus(State.COMPLETE);
    }
}
