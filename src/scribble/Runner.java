package scribble;

import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Runner {
    //TODO
    //run multiple sketches in serial
    //runs against provided test
    Class<?> applicationClass = null;
    LinkedList<Class<?>> classList = new LinkedList<>();
    CustomClassLoader sketchLoader = new CustomClassLoader();
    PApplet processingApp;

    public void runAll(SketchBook sb){
        int i=0;
        Sketch sk;
        File[] classes;
        while((sk = sb.getSketch(i)) != null){
            if(sk.getStatus() == "COMPILED"){
                //load compiled sketch into custom class loader
                classes = sk.compiledDirectory.toFile().listFiles();

                try{
                    for(File classFile : classes){
                        if(classFile.getName().equals(sk.getSketchName()+".class")){
                            applicationClass = sketchLoader.loadSketch(classFile.toPath());
                        } else if (classFile.getName().contains(".class")){
                            classList.add(sketchLoader.loadSketch((classFile.toPath())));
                        }
                    }

                    this.run(sk);

                } catch(IOException e){
                    System.out.println("Failed to load classes for " + sk.getSubmissionName());
                }

            }
            sketchLoader = new CustomClassLoader();
            classList.clear();
            i++;
        }
        processingApp.exit();
    }

    public void run(Sketch sk){
        try{
            processingApp = (PApplet) applicationClass.getConstructor().newInstance();

            System.out.println("Running sketch " + sk.submissionName);
            PApplet.runSketch(new String[]{ applicationClass.getName()}, processingApp);
            processingApp.noLoop();
            while (true){
                processingApp.redraw();
                //System.out.println(processingApp.frameCount);
                if(processingApp.frameCount > 100){
                    //processingApp.exit();
                    processingApp.stop();
                    break;
                }
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public void run(SketchBook sb, int i){

    }
}
