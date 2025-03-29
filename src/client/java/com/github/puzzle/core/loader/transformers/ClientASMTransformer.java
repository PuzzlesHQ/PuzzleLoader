package com.github.puzzle.core.loader.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.util.Objects;

public class ClientASMTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        String[] parts = name.split("\\.");
        if (Objects.equals(parts[parts.length - 1], "ItemCatalogWidget")) {
            System.err.println(name);
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);

            reader.accept(new ItemRegistryEnforcerClassTransformer(writer), 2);
            return writer.toByteArray();
        }
        if (Objects.equals(parts[parts.length - 1], "GameMusicManager")) {
            System.err.println(name);
            ClassReader reader = new ClassReader(basicClass);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);

            reader.accept(new MusicRegistryEnforcerClassTransformer(writer), 2);
            return writer.toByteArray();
        }
        return basicClass;
    }

}
