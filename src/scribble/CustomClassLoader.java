package scribble;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class CustomClassLoader extends ClassLoader {

    public Class<?> loadSketch(Path sketchPath) throws IOException {
        String sketchPathStr = sketchPath.toString();
        byte[] rawClassData = loadClassData(sketchPathStr);
        byte[] publicClassData = widenClass(rawClassData);
        return prepareClass(publicClassData);
    }
    public Class<?> loadTestSpec(Path testSpecPath) throws IOException {
        String sketchPathStr = testSpecPath.toString();
        byte[] rawClassData = loadClassData(sketchPathStr);
        return prepareClass(rawClassData);
    }

    private Class<?> prepareClass(byte[] classBytes) {
        Class<?> newClass = defineClass(null, classBytes, 0, classBytes.length);
        resolveClass(newClass);
        return newClass;
    }

    private byte[] loadClassData(String path) throws IOException {
        try (InputStream fileReader = new FileInputStream(path)) {
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