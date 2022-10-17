package scribble;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class CustomClassLoader extends ClassLoader {

    public Class<?> loadSketch(Sketch sketch) throws IOException {

        File sketchDirectoryFile = sketch.compiledDirectory.toFile();
        File[] sketchClassFiles = sketchDirectoryFile.listFiles(f->f.getName().endsWith(".class"));

        Class<?> mainClass = null;
        assert sketchClassFiles != null;
        for (File cf : sketchClassFiles) {
            byte[] rawClassData = loadClassData(cf);
            byte[] publicClassData = widenClass(rawClassData);
            Class<?> unknownClass = prepareClass(publicClassData);

            if (unknownClass.getName().equals(sketch.sketchName)) {
                mainClass = unknownClass;
            }
        }

        return mainClass;

    }
    public Class<?> loadPath(Path testSpecPath) throws IOException {
        byte[] rawClassData = loadClassData(testSpecPath.toFile());
        return prepareClass(rawClassData);
    }

    private Class<?> prepareClass(byte[] classBytes) {
        Class<?> newClass = defineClass(null, classBytes, 0, classBytes.length);
        resolveClass(newClass);
        return newClass;
    }

    private byte[] loadClassData(File file) throws IOException {
        try (InputStream fileReader = new FileInputStream(file)) {
            return fileReader.readAllBytes();
        }
    }

    public byte[] widenClass(byte[] classBytes) {
        ClassWriter cw = new ClassWriter(Opcodes.ASM9);

        ClassVisitor cv = new AccessWidener(Opcodes.ASM9, cw);

        ClassReader cr = new ClassReader(classBytes);
        cr.accept(cv, 0);
        return cw.toByteArray();

    }
}