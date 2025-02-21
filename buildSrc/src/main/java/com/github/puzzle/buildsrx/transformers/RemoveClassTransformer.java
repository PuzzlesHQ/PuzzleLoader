package com.github.puzzle.buildsrx.transformers;

import java.util.ArrayList;
import java.util.List;

public class RemoveClassTransformer extends AbstractClassTransformer {

    public static List<String> classesToRemove = new ArrayList<>();

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
    public boolean keepClass() {
        return keepClass;
    }
}
