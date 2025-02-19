package com.github.puzzle.buildsrx.hash;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    public static void createHashes(File f) {
        Hasher.createSHA1(f);
        Hasher.createMD5(f);
    }

    public static File createSHA1(File f) {
        File sha1 = new File(f.getAbsolutePath() + ".sha1");
        if (!sha1.exists()) {
            try {
                sha1.createNewFile();
            } catch (IOException e) {
                System.out.println(sha1);
                throw new RuntimeException(e);
            }
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        try (InputStream is = new FileInputStream(f);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            FileOutputStream stream = new FileOutputStream(sha1);
            stream.write(md.digest());
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sha1;
    }

    public static File createMD5(File f) {
        System.out.println(f.getAbsolutePath() + ".md5");
        File md5 = new File(f.getAbsolutePath() + ".md5");
        if (!md5.exists()) {
            try {
                md5.createNewFile();
            } catch (IOException e) {
                System.out.println(md5);
                throw new RuntimeException(e);
            }
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }

        try (InputStream is = new FileInputStream(f);
             DigestInputStream dis = new DigestInputStream(is, md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            FileOutputStream stream = new FileOutputStream(md5);
            stream.write(md.digest());
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return md5;
    }

}
