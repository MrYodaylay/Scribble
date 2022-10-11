package scribble;

import javax.tools.JavaCompiler;

public class Main {
    public static void main(String[] args) {
        // settings setup
        Settings settings = new Settings(args);

        // build test file
        Builder b = new Builder();
        b.buildTest(settings.getTest());
        //maybe change the .java to .class


        // sketch loader
        SketchBook sb = new SketchBook("temp");


        // builder sketches in sketchbook
        b.build(sb);

        // runner
        Runner rn = new Runner();

        // takes sketchbook and test file
        rn.runAll(sb);
        rn.run(sb,1);

    }
}
