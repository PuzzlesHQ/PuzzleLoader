package com.github.puzzle.buildsrx.transformers;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.HashMap;
import java.util.Map;

public class RedirectClassTransformer extends AbstractClassTransformer {

    public static Map<String, String> redirections = new HashMap<>();

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (signature != null) {
            for (String key : RedirectClassTransformer.redirections.keySet()) {
                if (signature.contains(key)) signature = signature.replaceAll(key, RedirectClassTransformer.redirections.get(key));
            }
        }

        if (superName != null) {
            for (String key : RedirectClassTransformer.redirections.keySet()) {
                if (superName.contains(key)) superName = superName.replaceAll(key, RedirectClassTransformer.redirections.get(key));
            }
        }

        if (interfaces != null && interfaces.length != 0) {
            for (int i = 0; i < interfaces.length; i++) {
                for (String key : RedirectClassTransformer.redirections.keySet()) {
                    if (interfaces[i].contains(key)) interfaces[i] = interfaces[i].replaceAll(key, RedirectClassTransformer.redirections.get(key));
                }
            }
        }

        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        String descriptorRedirect = RedirectClassTransformer.redirections.get(descriptor);
        if (descriptorRedirect == null)
            descriptorRedirect = descriptor;

        if (signature != null) {
            for (String key : RedirectClassTransformer.redirections.keySet()) {
                if (signature.contains(key)) signature = signature.replaceAll(key, RedirectClassTransformer.redirections.get(key));
            }
        }

        return super.visitField(access, name, descriptorRedirect, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (signature != null) {
            for (String key : RedirectClassTransformer.redirections.keySet()) {
                if (signature.contains(key)) signature = signature.replaceAll(key, RedirectClassTransformer.redirections.get(key));
            }
        }

        for (String key : RedirectClassTransformer.redirections.keySet()) {
            if (descriptor.contains(key)) descriptor = descriptor.replaceAll(key, RedirectClassTransformer.redirections.get(key));
        }

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
