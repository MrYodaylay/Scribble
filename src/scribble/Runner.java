package scribble;

import processing.core.PApplet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Runner {
    //TODO
    //runs against provided test

    public void runAll(SketchBook sb){

        Logger.info("Runner: Preparing to execute %d sketches.".formatted(sb.size()));

        for (Sketch s : sb) {
            if (s.status.equals("COMPILED")) {
                run(s);
                System.gc();
            } else {
                Logger.info("Runner: Skipping sketch %s/%s".formatted(s.submissionName, s.sketchName));
            }
        }
    }

    public void run(Sketch sk) {
        CustomClassLoader sketchLoader = new CustomClassLoader();

        Logger.info("Runner: Executing sketch %s/%s".formatted(sk.getSubmissionName(), sk.getSketchName()));

        // Attempt to load the sketch class
        Class<?> sketchClass;
        try {
            sketchClass = sketchLoader.loadSketch(sk);
        } catch (IOException e) {
            Logger.error("Runner: Failed to load sketch.");
            return;
        }

        // Run the sketch
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
        processingApp.getSurface().setVisible(false);

        while (processingApp.frameCount < 100){
            processingApp.redraw();
        }

        processingApp.dispose();
    }
}
