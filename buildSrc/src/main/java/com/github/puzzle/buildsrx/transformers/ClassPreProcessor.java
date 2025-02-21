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
