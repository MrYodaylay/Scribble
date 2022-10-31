package scribble.sketch;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class AccessWidener extends ClassVisitor {

    public AccessWidener(int opcode, ClassVisitor cv) {
        super(opcode, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, Opcodes.ACC_PUBLIC, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return super.visitField(Opcodes.ACC_PUBLIC, name, descriptor, signature, value);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, Opcodes.ACC_PUBLIC);
    }
}
