package com.github.puzzle.core.loader.localization;

import com.github.puzzle.core.loader.launch.Piece;

public record TranslationKey(
        String identifier,
        int hash,
        String namespace,
        String[] groups
) {

    public static final String defaultNamespace = Piece.provider.getDefaultNamespace();

    public TranslationKey(
            String identifier
    ) {
        this(identifier, identifier.hashCode(), getNamespace(identifier), getGroups(identifier));
    }

    private static String getNamespace(String identifier) {
        return null;
    }

    private static String[] getGroups(String identifier) {
        return null;
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
