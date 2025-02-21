package com.github.puzzle.buildsrx;

import com.github.puzzle.buildsrx.transformers.AbstractClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GenericTransformer {

    public static void process(File f, AbstractClassTransformer... transformers) {
        try {
            InputStream byteStream = new FileInputStream(f);
            byte[] bytes = byteStream.readAllBytes();
            byteStream.close();

            ZipInputStream input = new ZipInputStream(new ByteArrayInputStream(bytes));
            ZipEntry entry = input.getNextEntry();
            while (entry != null) {
                String entryName = entry.getName();

                if (entryName.endsWith(".class") && !entryName.contains("module-info") && !entryName.contains("package-info")) {
                    transformClass(input.readAllBytes(), transformers);
                }

                entry = input.getNextEntry();
            }
            input.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void transform(File f, AbstractClassTransformer... transformers) {
        try {
            InputStream byteStream = new FileInputStream(f);
            byte[] bytes = byteStream.readAllBytes();
            byteStream.close();

            ZipInputStream input = new ZipInputStream(new ByteArrayInputStream(bytes));
            ZipOutputStream output = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry entry = input.getNextEntry();
            while (entry != null) {
                String entryName = entry.getName();

                if (entryName.contains("module-info") || entryName.contains("package-info")) {
                    output.putNextEntry(entry);
                    output.write(input.readAllBytes());
                } else if (entryName.endsWith(".class")) {
                    byte[] classBytes = transformClass(input.readAllBytes(), transformers);
                    if (classBytes != null) {
                        output.putNextEntry(entry);
                        output.write(classBytes);
                    }
                } else {
                    output.putNextEntry(entry);
                    output.write(input.readAllBytes());
                }

                entry = input.getNextEntry();
            }
            input.close();
            output.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] transformClass(byte[] bytes, AbstractClassTransformer... transformers) {
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);

        if (transformers.length == 1) {
            transformers[0].setWriter(writer);
            transformers[0].setClassName(reader.getClassName().replaceAll("\\.", "/"));
            reader.accept(transformers[0], 2);
        } else if (transformers.length > 1){
            for (AbstractClassTransformer transformer : transformers) {
                transformer.setWriter(writer);
                transformer.setClassName(reader.getClassName().replaceAll("\\.", "/"));
                reader.accept(transformer, 2);

                bytes = writer.toByteArray();
                reader = new ClassReader(bytes);
                writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);

                if (!transformer.keepClass()) {
                    bytes = null;
                    break;
                }
            }
        }

        return bytes;
    }

//    private static byte[] transformClass(byte[] bytes, AbstractClassTransformer... transformers) {
//        ClassReader reader = new ClassReader(bytes);
//        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
//
//        for (AbstractClassTransformer transformer : transformers) {
//            transformer.setWriter(writer);
//            transformer.setClassName(reader.getClassName().replaceAll("\\.", "/"));
//            reader.accept(transformer, 2);
//        }
//
//        return writer.toByteArray();
//    }

}
