package com.github.puzzle.game.items.data.attributes;

import com.github.puzzle.game.items.data.DataTag;
import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;

@Deprecated(forRemoval = true, since = "2.3.9")
public class LongDataAttribute implements DataTag.DataTagAttribute<Long> {

    long data;

    public LongDataAttribute() {}

    public LongDataAttribute(long data) {
        this.data = data;
    }

    public LongDataAttribute(Long data) {
        this.data = data;
    }

    @Override
    public void setValue(Long value) {
        this.data = value;
    }

    @Override
    public Long getValue() {
        return data;
    }

    @Override
    public DataTag.DataTagAttribute<Long> copyAndSetValue(Long value) {
        return new LongDataAttribute(value);
    }

    @Override
    public String getFormattedString() {
        return Long.toString(data);
    }

    @Override
    public String toString() {
        return getFormattedString();
    }

    @Override
    public void read(CRBinDeserializer crBinDeserializer) {
        this.data = crBinDeserializer.readLong("data_value", 0);
    }

    @Override
    public void write(CRBinSerializer crBinSerializer) {
        crBinSerializer.writeLong("data_value", data);
    }
}
