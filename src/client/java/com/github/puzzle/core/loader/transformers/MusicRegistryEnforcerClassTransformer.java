package com.github.puzzle.core.loader.transformers;

import com.badlogic.gdx.utils.Array;
import com.github.puzzle.core.loader.util.Reflection;
import com.github.puzzle.core.registries.IRegistry;
import com.github.puzzle.game.ClientPuzzleRegistries;
import finalforeach.cosmicreach.audio.GameMusicManager;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Objects;
import java.util.function.BiFunction;

public class MusicRegistryEnforcerClassTransformer extends ClassVisitor {

    protected MusicRegistryEnforcerClassTransformer(ClassVisitor classVisitor) {
        super(Opcodes.ASM9, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("playSongWithCriteria")) {
            return new GameMusicManagerMethodTransformer(super.visitMethod(access, name, descriptor, signature, exceptions));
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    public static class GameMusicManagerMethodTransformer extends MethodVisitor {

        protected GameMusicManagerMethodTransformer(MethodVisitor methodVisitor) {
            super(Opcodes.ASM9, methodVisitor);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            BiFunction<Class<?>, String, Boolean> check = (klass, field) ->
                    Objects.equals(owner, klass.getName().replaceAll("\\.", "/"))
                    && Objects.equals(name, field)
                    && Objects.equals(descriptor, Reflection.getField(klass, field).getType().descriptorString());

            if (
                    opcode == Opcodes.GETSTATIC && check.apply(GameMusicManager.class, "gameSongs")
            ) {
                miniVisiFieldInsn(ClientPuzzleRegistries.class, "SONGS");
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
                if (check.apply(Array.class, "iterator")) {
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
