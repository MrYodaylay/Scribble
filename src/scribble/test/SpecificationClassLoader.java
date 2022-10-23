package scribble.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class SpecificationClassLoader extends ClassLoader {

    public Class<?> loadPath(Path testSpecPath) throws IOException {
        byte[] rawClassData = loadClassData(testSpecPath.toFile());
        return prepareClass(rawClassData);
    }

    private byte[] loadClassData(File file) throws IOException {
        try (InputStream fileReader = new FileInputStream(file)) {
            return fileReader.readAllBytes();
        }
    }

    private Class<?> prepareClass(byte[] classBytes) {
        Class<?> newClass = defineClass(null, classBytes, 0, classBytes.length);
        resolveClass(newClass);
        return newClass;
    }
}
