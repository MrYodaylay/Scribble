package scribble;

import javax.tools.JavaCompiler;
import javax.tools.Tool;
import javax.tools.ToolProvider;

public class Builder {

    //compiles a pde to java
    //returns class directory

    public void build(SketchBook sb){

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
