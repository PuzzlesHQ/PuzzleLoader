package com.github.puzzle.core.localization;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TranslationKey {
	private String identifier;

	private int hashCode;

	@Contract(pure = true)
	public TranslationKey(@NotNull String identifier) {
		this.identifier = identifier;
		this.hashCode = identifier.hashCode();
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof TranslationKey key && key.identifier.equals(identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String toString() {
		return identifier;
	}

	public TranslationKey set(String key) {
		identifier = key;
		hashCode = key.hashCode();

		return this;
	}
}
