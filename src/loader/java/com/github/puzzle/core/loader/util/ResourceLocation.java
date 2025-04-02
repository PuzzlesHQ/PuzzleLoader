package com.github.puzzle.core.loader.util;

import com.github.puzzle.core.loader.launch.Piece;

import java.util.regex.Pattern;

public class ResourceLocation {

    final String id;
    final int hash;
    final String namespace;
    final String path;

    static final Pattern ID_SPLITTER_PATTERN = Pattern.compile(":");

    private ResourceLocation(String namespace, String path) {
        id = namespace + ":" + path;
        hash = id.hashCode();
        this.path = path;
        this.namespace = namespace;
    }

    public static ResourceLocation of(String namespace, String name) {
        return new ResourceLocation(namespace, name);
    }

    public static ResourceLocation of(String id) {
        String[] parts = ID_SPLITTER_PATTERN.split(id);
        if (parts.length < 2)
            return new ResourceLocation(Piece.provider.getDefaultNamespace(), parts[0]);
        if (parts.length > 2)
            throw new RuntimeException("Invalid ResourceLocation Format \"" + id + "\", must be formatted like \"namespace:path\"");

        return new ResourceLocation(parts[0], parts[1]);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    public String toPath() {
        return "/assets/" + namespace + (path.startsWith("/") ? path : "/" + path);
    }

    @Override
    public String toString() {
        return id;
    }
}
