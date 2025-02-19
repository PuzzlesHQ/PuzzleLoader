package com.github.puzzle.buildsrx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Util {

    public static void clearDir(File f) {
        if (f.isDirectory()) {
            for (File f0 : f.listFiles()) {
                delete(f0);
            }
        }
    }


    public static void delete(File f) {
        if (f.isDirectory()) {
            for (File f0 : f.listFiles()) {
                delete(f0);
            }
        }
        f.delete();
    }

    static void zip(File dir, File zip) throws IOException {
        if (!zip.exists()) zip.createNewFile();

        ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(zip));
        for (File f: dir.listFiles()) {
            stream.putNextEntry(new ZipEntry(f.getName()));
            FileInputStream s = new FileInputStream(f);
            stream.write(s.readAllBytes());
            s.close();
            stream.closeEntry();
        }
        stream.close();
    }
}
