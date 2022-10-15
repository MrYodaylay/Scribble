package scribble;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Builder {

    //compiles a pde to java
    //returns class directory

    public void build(SketchBook sb){
        int i=0;
        String start = "processing-java";
        String sketch = "--sketch=\'";
        String output = "--output=\'";
        String build = "--force --build";
        while(sb.getSketch(i) != null){
            //command building
            Sketch sk = sb.getSketch(i);
            sketch =sketch + sk.getSketchDirectory().toString();
            output =output + sk.getSketchDirectory().toString();

            sk.setStatus( compile(start + " " + sketch + "\' " + output + "\' " + build) );

            System.out.println(start + " " + sketch + "\' test**" + output + "\' " + build);

            i++;
        }
    }

    private String compile(String command){
        try{
            Runtime rt = Runtime.getRuntime();
            Process compilation = rt.exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(compilation.getInputStream()));
            String s = null;
            while ((s = in.readLine()) != null) {
                System.out.println(s);
            }
            BufferedReader er = new BufferedReader(new InputStreamReader(compilation.getErrorStream()));
            String ser = null;
            while ((ser = in.readLine()) != null) {
                System.out.println(ser);
            }

            try {
                compilation.waitFor(10, TimeUnit.SECONDS);
            } catch (InterruptedException ignored) {
                ;
            }
            compilation.destroy();

        } catch (IOException e){
            System.out.println("Unable to compile " + command);
            return "ERROR";
        }
        return "COMPILED";
    }

    public void buildTest(String testPath){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //TODO
        int compilationSuccess = compiler.run(null, null, null, testPath);

        if(compilationSuccess != 0){
            System.out.printf("shit broke in builder");
        }
    }
}
