package com.github.puzzle.core.loader.util;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.regex.Pattern;

public record Version(
        int major,
        int minor,
        int patch,
        Type type
) implements Comparator<Version>, Comparable<Version> {

    @Override
    public int compare(Version o1, Version o2) {
        return o1.compareTo(o2);
    }

    @Override
    public int compareTo(@NotNull Version o) {
        if (equals(o)) return 0;

        if (o.type.ordinal() > type.ordinal()) return -1;
        if (o.type.ordinal() < type.ordinal()) return 1;

        if (o.major != major) return Integer.compare(major, o.major);
        if (o.minor != minor) return Integer.compare(minor, o.minor);
        return Integer.compare(patch, o.patch);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Version v) {
            return v.major == major && v.minor == minor && v.patch == patch && v.type == type;
        }
        return false;
    }

    public enum Type {

        PRE_ALPHA("p"),
        ALPHA("a"),
        BETA("b"),
        GAMMA("g"),
        RELEASE_CANDIDATE("rc"),
        RELEASE("r");

        final String matcherString;

        Type(String matcherString) {
            this.matcherString = matcherString;
        }

        public static Type find(String typeString) {
            for (Type t : Type.values()) {
                if (t.matcherString.equals(typeString.toLowerCase())) {
                    return t;
                }
            }
            return Type.RELEASE;
        }

        @Override
        public String toString() {
            return matcherString;
        }
    }

    static final Pattern dotSplit = Pattern.compile("\\.");
    static final Pattern dashSplit = Pattern.compile("-");

    static final Pattern validator = Pattern.compile("^([0-9]+\\.[0-9]+\\.[0-9]+){1}(-[PRGprgA-Ba-b]|-rc|-RC|)$");

    public static Version parse(String version) {
        if (validator.matcher(version).matches())
            throw new IllegalArgumentException("Version must fit within \"number.number\" or \"number.number.number\" with an optional -a, -b, -r, -p ex:0.4.0-rc");

        String[] parts = dotSplit.split(version);
        String endPart = parts[parts.length - 1];

        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = 0;

        Type t = Type.RELEASE;

        if (endPart.contains("-")) {
            String typeString = dashSplit.split(endPart, 1)[0];

            t = Type.find(typeString);

            if (parts.length > 2)
                patch = Integer.parseInt(endPart.replaceAll("-" + typeString, ""));
        } else {
            if (parts.length > 2)
                patch = Integer.parseInt(endPart);
        }

        return new Version(
                major, minor, patch, t
        );
    }

    @Override
    public String toString() {
        return major + "-" + minor + "-" + patch + "-" + type;
    }
}
