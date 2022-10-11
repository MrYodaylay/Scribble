package scribble;



public class Main {
    public static void main(String[] args) {
        // command line interpret
        //sketchFolder, testFolder, output, other

        //convert strings to path

        // sketch loader
        SketchBook sb = new SketchBook("temp");


        // builder
        Builder b = new Builder();
        b.build(sb);

        // runner
        Runner rn = new Runner();
        rn.runAll(sb);
        rn.run(sb,1);

    }
}
