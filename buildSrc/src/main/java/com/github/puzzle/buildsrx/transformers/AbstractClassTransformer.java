package com.github.puzzle.buildsrx.transformers;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class AbstractClassTransformer extends ClassVisitor {

    String className;

    protected AbstractClassTransformer() {
        super(Opcodes.ASM9);
    }

    public void setWriter(ClassWriter writer) {
        cv = writer;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean keepClass() {
        return true;
    }

    protected String simpleName(String className) {
        String[] parts = className.split("/");
        return parts[parts.length - 1];
    }
}
