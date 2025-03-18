package com.github.puzzle.core.loader.provider.mod;

public class AdapterPathPair {

    String adapter;
    String value;
    Object instance;

    public AdapterPathPair(String adapter, String value) {
        this.adapter = adapter;
        this.value = value;
    }

    public String getAdapter() {
        return adapter;
    }

    public String getValue() {
        return value;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        if (instance == null)
            this.instance = instance;
    }
}
