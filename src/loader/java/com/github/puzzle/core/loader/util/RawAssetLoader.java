package com.github.puzzle.core.loader.util;

import com.github.puzzle.core.Constants;
import com.github.puzzle.core.loader.launch.Piece;
import com.github.puzzle.core.loader.launch.PuzzleClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class RawAssetLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger("Puzzle | RawAssetLoader");

    private static byte[] getBytesFromStream(InputStream stream) {
        try {
            byte[] bytes = stream.readAllBytes();
            stream.close();
            return bytes;
        } catch (Exception ignore) {
            return null;
        }
    }

    public static RawFileHandle getLowLevelZipAsset(ZipFile file, String path) {
        try {
            ZipEntry entry = file.getEntry(path);
            InputStream stream = file.getInputStream(entry);
            String[] strings = entry.getName().split("/");
            return new RawFileHandle(getBytesFromStream(stream), strings[strings.length - 1]);
        } catch (Exception ignore) {
            LOGGER.error("Cannot find resource {} in zip {}.", path, file);
            return null;
        }
    }

    public static RawFileHandle getZipAsset(ZipFile file, ResourceLocation location) {
        try {
            ZipEntry entry = file.getEntry(location.toPath());
            InputStream stream = file.getInputStream(entry);
            String[] strings = entry.getName().split("/");
            return new RawFileHandle(getBytesFromStream(stream), strings[strings.length - 1]);
        } catch (Exception ignore) {
            LOGGER.error("Cannot find resource {} in zip {}.", location, file);
            return null;
        }
    }

    public static RawFileHandle getLowLevelClassPathAsset(String path) {
        URL url = Constants.class.getResource(path);
        if (url == null) {
            try {
                url = PuzzleClassLoader.class.getClassLoader().getResource(path);
                return new RawFileHandle(getBytesFromStream(url.openStream()), url.getFile());
            } catch (Exception ignore) {
                LOGGER.error("Cannot find resource {} on the classpath.", path);
                return null;
            }
        }
        try {
            return new RawFileHandle(getBytesFromStream(url.openStream()), url.getFile());
        } catch (Exception ignore) {
            LOGGER.error("Cannot find resource {} on the classpath.", path);
            return null;
        }
    }

    public static RawFileHandle getClassPathAsset(ResourceLocation location) {
        URL url = Constants.class.getResource(location.toPath());
        if (url == null) {
            try {
                url = PuzzleClassLoader.class.getClassLoader().getResource(location.toPath());
                return new RawFileHandle(getBytesFromStream(url.openStream()), url.getFile());
            } catch (Exception ignore) {
                LOGGER.error("Cannot find resource {} on the classpath.", location);
                return null;
            }
        }
        try {
            return new RawFileHandle(getBytesFromStream(url.openStream()), url.getFile());
        } catch (Exception ignore) {
            LOGGER.error("Cannot find resource {} on the classpath.", location);
            return null;
        }
    }

    public static RawFileHandle getLowLevelRelativeAsset(File dir, String path) {
        try {
            URL url = new File(dir, path).toURI().toURL();
            InputStream stream = url.openStream();
            return new RawFileHandle(getBytesFromStream(stream), url.getFile());
        } catch (IOException ignore) {
            LOGGER.error("Cannot find resource relative {} in dir {}", path, dir);
            return null;
        }
    }

    public static RawFileHandle getRelativeAsset(File dir, ResourceLocation location) {
        try {
            URL url = new File(dir, location.toPath()).toURI().toURL();
            InputStream stream = url.openStream();
            return new RawFileHandle(getBytesFromStream(stream), url.getFile());
        } catch (IOException ignore) {
            LOGGER.error("Cannot find resource relative {} in dir {}", location, dir);
            return null;
        }
    }

    public static class RawFileHandle {

        byte[] bytes;
        String file;

        public RawFileHandle(byte[] bytes, String file) {
            this.bytes = bytes;
            this.file = file;
        }

        public String getFile() {
            return file;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public String getString() {
            return new String(getBytes());
        }

        public void dispose() {
            bytes = null;
            file = null;
        }
    }

}
