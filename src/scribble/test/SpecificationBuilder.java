package scribble.test;

import scribble.log.Logger;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

public class SpecificationBuilder {

    public Path compile (Path testJavaFilePath) {

        File testJavaFile = testJavaFilePath.toFile();
        File[] files = {testJavaFile};

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fm = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> cu = fm.getJavaFileObjectsFromFiles(Arrays.asList(files));

        Writer out = new StringWriter();
        boolean r = compiler.getTask(out, fm, null, null, null, cu).call();

        if (!r) {
            Logger.warning("Java compiler returned an error.");
            Logger.message(out.toString());
        }

        try {
            fm.close();
        } catch (IOException e) {
            Logger.error("An IOException occurred when attempting to compile the test specification file.");
            Logger.message(e.getMessage());
            return null;
        }

        // FUTURE Instead of placing the .class file alongside the .java file, place it in the temp directory
        String classFileName = testJavaFilePath.getFileName().toString().replace(".java", ".class");
        return testJavaFilePath.resolveSibling(classFileName);
    }


    public Class<TestPlan> load (Path testClassFilePath) {

        SpecificationClassLoader testLoader = new SpecificationClassLoader();

        Class<?> testClass = null;
        try {
            testClass = testLoader.loadPath(testClassFilePath);
        } catch (IOException e) {
            Logger.error("An IOException occurred when loading the test class file.");
            Logger.message(e.getMessage());
            return null;
        }

        return (Class<TestPlan>) testClass;

    }

}
