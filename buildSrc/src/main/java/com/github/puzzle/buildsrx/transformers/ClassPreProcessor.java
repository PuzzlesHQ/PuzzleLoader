package com.github.puzzle.buildsrx.transformers;

import com.github.puzzle.buildsrx.GameScanner;
import io.github.puzzle.cosmic.util.Alternative;
import io.github.puzzle.cosmic.util.ApiGen;
import io.github.puzzle.cosmic.util.Temporary;
import org.objectweb.asm.*;

import java.util.Objects;

public class ClassPreProcessor extends AbstractClassTransformer {

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (Objects.equals(descriptor, Temporary.class.descriptorString()))
            RemoveClassTransformer.classesToRemove.add(className);

        return new AnnotationScanner(descriptor, super.visitAnnotation(descriptor, visible));
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        return new FieldScanner(super.visitField(access, name, descriptor, signature, value), name);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return new MethodScanner(super.visitMethod(access, name, descriptor, signature, exceptions), name, descriptor);
    }

    public class FieldScanner extends FieldVisitor {

        public String name;

        protected FieldScanner(FieldVisitor methodVisitor, String name) {
            super(Opcodes.ASM9, methodVisitor);
            this.name = name;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (Objects.equals(descriptor, Temporary.class.descriptorString()))
                RemoveClassTransformer.fieldsToRemove.add(className+name);

            return super.visitAnnotation(descriptor, visible);
        }
    }

    public class MethodScanner extends MethodVisitor {

        public String name;
        public String descriptor;

        protected MethodScanner(MethodVisitor methodVisitor, String name, String descriptor) {
            super(Opcodes.ASM9, methodVisitor);
            this.name = name;
            this.descriptor = descriptor;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (Objects.equals(descriptor, Temporary.class.descriptorString()))
                RemoveClassTransformer.methodsToRemove.add(className + name + this.descriptor);

            return super.visitAnnotation(descriptor, visible);
        }
    }

    public class AnnotationScanner extends AnnotationVisitor {

        String descriptor;

        protected AnnotationScanner(String descriptor, AnnotationVisitor visitor) {
            super(Opcodes.ASM9);

            this.descriptor = descriptor;
            av = visitor;
        }

        @Override
        public void visit(String name, Object value) {
            if (Objects.equals(descriptor, Alternative.class.descriptorString())){
                if (name.equals("value")) {
                    String redirection = GameScanner.findClassByNameNoDupes((String) value).replaceAll("\\.", "/");
                    RedirectClassTransformer.redirections.put(className, redirection);
                    RedirectClassTransformer.redirections.put("L" + className + ";", "L" + redirection + ";");
                }
            }
            if (Objects.equals(descriptor, ApiGen.class.descriptorString())){
                if (name.equals("value")) {
                    ApiClassTransformer.apiGenerationMap.put(className, GameScanner.findClassByNameNoDupes((String) value).replaceAll("\\.", "/"));
                }
            }
            super.visit(name, value);
        }
    }

}
