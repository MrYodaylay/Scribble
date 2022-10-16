package scribble;

import processing.core.PApplet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Runner {
    //TODO
    //makes variables public
    //runs against provided test
    Class<?> applicationClass = null;
    LinkedList<Class<?>> classList = new LinkedList<>();
    CustomClassLoader sketchLoader = new CustomClassLoader();

    public void runAll(SketchBook sb){
        int i=0;
        Sketch sk;
        File[] classes;
        while((sk = sb.getSketch(i)) != null){
            if(sk.getStatus() == "COMPILED"){
                //load compiled sketch into custom class loader
                classes = sk.compiledDirectory.listFiles();

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
    }

    public void run(Sketch sk){
        try{
            PApplet processingApp = (PApplet) applicationClass.getConstructor().newInstance();

            System.out.println("Running sketch " + sk.submissionName);
            PApplet.runSketch(new String[]{ applicationClass.getName()}, processingApp);
            processingApp.noLoop();
            while (true){
                processingApp.redraw();
                //System.out.println(processingApp.frameCount);
                if(processingApp.frameCount > 100){
                    processingApp.exit();
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
