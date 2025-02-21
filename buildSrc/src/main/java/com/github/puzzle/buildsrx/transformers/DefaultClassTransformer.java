package com.github.puzzle.buildsrx.transformers;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

public class DefaultClassTransformer extends AbstractClassTransformer {

    static final String literalTemplate = "className.method is implemented by custom puzzle impl or mixin, if this error is caught contact the developer.";

    boolean canProcess = false;

    @Override
    public void setClassName(String className) {
        super.setClassName(className);

        canProcess = false;
        visitors.clear();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if ((access & Opcodes.ACC_INTERFACE) != 0 && ApiClassTransformer.apiGenerationMap.containsKey(className))
            canProcess = true;

        super.visit(version, access, name, signature, superName, interfaces);
    }

    List<FakeMethod> visitors = new ArrayList<>();

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (canProcess && (access & Opcodes.ACC_ABSTRACT) != 0) {
            visitors.add(new FakeMethod(
                    access - Opcodes.ACC_ABSTRACT,
                    name,
                    descriptor,
                    signature,
                    exceptions
            ));
            return null;
        }

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    record FakeMethod(
            int access,
            String name,
            String descriptor,
            String signature,
            String[] exceptions
    ) {}

    @Override
    public void visitEnd() {
        for (FakeMethod v : visitors) {
            implementDefault(visitMethod(v.access, v.name, v.descriptor, v.signature, v.exceptions), v.name);
        }
        super.visitEnd();
    }

    private void implementDefault(MethodVisitor visitor, String name) {
        visitor.visitCode();
        visitor.visitTypeInsn(Opcodes.NEW, "java/lang/RuntimeException");
        visitor.visitInsn(Opcodes.DUP);
        String message = literalTemplate;
        message = message.replaceAll("className", simpleName(className).replaceAll("\\$", ""));
        message = message.replaceAll("method", name);
        visitor.visitLdcInsn(message);
        visitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/RuntimeException", "<init>", "(Ljava/lang/String;)V", false);
        visitor.visitInsn(Opcodes.ATHROW);
        visitor.visitMaxs(5, 5);
        visitor.visitEnd();
    }

}
