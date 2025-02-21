package com.github.puzzle.buildsrx.transformers;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class RemoveClassTransformer extends AbstractClassTransformer {

    public static List<String> classesToRemove = new ArrayList<>();
    public static List<String> methodsToRemove = new ArrayList<>();
    public static List<String> fieldsToRemove = new ArrayList<>();

    boolean keepClass = true;

    @Override
    public void setClassName(String className) {
        super.setClassName(className);

        keepClass = true;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (classesToRemove.contains(name)) {
            keepClass = false;
            return;
        }

        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (methodsToRemove.contains(className + name + descriptor))
            return null;

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        if (fieldsToRemove.contains(className + name))
            return null;

        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public boolean keepClass() {
        return keepClass;
    }
}
