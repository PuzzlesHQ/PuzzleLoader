package com.github.puzzle.core.loader.transformers;

import com.badlogic.gdx.utils.ObjectMap;
import com.github.puzzle.core.loader.util.Reflection;
import com.github.puzzle.core.registries.IRegistry;
import com.github.puzzle.game.PuzzleRegistries;
import finalforeach.cosmicreach.items.Item;
import org.objectweb.asm.*;

import java.util.Objects;
import java.util.function.BiFunction;

public class ItemCatalogClassVisitor extends ClassVisitor {

    protected ItemCatalogClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("getItems")) {
            return new ItemCatalogMethodTransformer(super.visitMethod(access, name, descriptor, signature, exceptions));
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public static class ItemCatalogMethodTransformer extends MethodVisitor {

        protected ItemCatalogMethodTransformer(MethodVisitor methodVisitor) {
            super(Opcodes.ASM9, methodVisitor);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            BiFunction<Class<?>, String, Boolean> check = (klass, field) ->
                    Objects.equals(owner, klass.getName().replaceAll("\\.", "/"))
                    && Objects.equals(name, field)
                    && Objects.equals(descriptor, "()" + Reflection.getField(klass, field).getType().descriptorString());

            if (
                    opcode == Opcodes.GETSTATIC && check.apply(Item.class, "allItems")
            ) {
                miniVisiFieldInsn(PuzzleRegistries.class, "ITEMS");
                return;
            }

            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            BiFunction<Class<?>, String, Boolean> check = (klass, method) ->
                    Objects.equals(owner, klass.getName().replaceAll("\\.", "/"))
                    && Objects.equals(name, method)
                    && Objects.equals(descriptor, "()" + Reflection.getMethod(klass, method).getReturnType().descriptorString());

            if (opcode == Opcodes.INVOKEVIRTUAL) {
                if (check.apply(ObjectMap.class, "values"))
                    return;

                if (check.apply(ObjectMap.Values.class, "iterator")) {
                    miniVisitMethodInsn(IRegistry.class, "iterator");
                    return;
                }
            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        private void miniVisiFieldInsn(Class<?> klass, String field) {
            super.visitFieldInsn(
                    Opcodes.GETSTATIC,
                    klass.getName().replaceAll("\\.", "/"),
                    field,
                    Reflection.getField(klass, field).getType().descriptorString()
            );
        }

        private void miniVisitMethodInsn(Class<?> klass, String method) {
            super.visitMethodInsn(
                    klass.isInterface() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL,
                    klass.getName().replaceAll("\\.", "/"),
                    method,
                    "()" + Reflection.getMethod(klass, method).getReturnType().descriptorString(),
                    klass.isInterface()
            );
        }
    }

}
