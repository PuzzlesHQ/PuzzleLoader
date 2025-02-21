package com.github.puzzle.buildsrx.transformers;

import org.gradle.internal.impldep.bsh.org.objectweb.asm.Constants;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

public class ApiClassTransformer extends AbstractClassTransformer {

    public static Map<String, String> apiGenerationMap = new HashMap<>();

    @Override
    public void visitEnd() {
        String implClass = apiGenerationMap.get(className);

        if (implClass == null) {
            super.visitEnd();
            return;
        }

        MethodVisitor as = super.visitMethod(
                Opcodes.ACC_PUBLIC,
                "as",
                "()L" + implClass + ";",
                null, new String[0]);
        implementDefault(as, implClass);

        MethodVisitor convert0 = super.visitMethod(
                Opcodes.ACC_PUBLIC | Constants.ACC_STATIC,
                "as",
                "(L" + implClass + ";)L" + className + ";",
                null, new String[0]
        );

        implementStatic(convert0, simpleName(implClass).toLowerCase(), className);

        MethodVisitor convert1 = super.visitMethod(
                Opcodes.ACC_PUBLIC | Constants.ACC_STATIC,
                "as",
                "(L" + className + ";)L" + implClass + ";",
                null, new String[0]);
        implementStatic(convert1, simpleName(className).toLowerCase(), implClass);

        super.visitEnd();
    }

    private void implementDefault(MethodVisitor visitor, String descriptor) {
        visitor.visitCode();
        visitor.visitVarInsn(Opcodes.ALOAD, 0);
        visitor.visitTypeInsn(Opcodes.CHECKCAST, Object.class.getName().replaceAll("\\.", "/"));
        visitor.visitTypeInsn(Opcodes.CHECKCAST, descriptor);
        visitor.visitInsn(Opcodes.ARETURN);
        visitor.visitMaxs(5, 5);
        visitor.visitEnd();
    }

    private void implementStatic(MethodVisitor visitor, String name, String descriptor) {
        visitor.visitCode();
        visitor.visitParameter(name, 0);
        visitor.visitVarInsn(Opcodes.ALOAD, 0);
        visitor.visitTypeInsn(Opcodes.CHECKCAST, Object.class.getName().replaceAll("\\.", "/"));
        visitor.visitTypeInsn(Opcodes.CHECKCAST, descriptor);
        visitor.visitInsn(Opcodes.ARETURN);
        visitor.visitMaxs(5, 5);
        visitor.visitEnd();
    }

}
