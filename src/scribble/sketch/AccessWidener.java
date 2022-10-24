package scribble.sketch;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class AccessWidener extends ClassVisitor {

    public AccessWidener(int opcode, ClassVisitor cv) {
        super(opcode, cv);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(Opcodes.ACC_PUBLIC, name, descriptor, signature, value);
    }

}
