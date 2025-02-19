package io.github.puzzle.cosmic.impl.data.point.array;

import finalforeach.cosmicreach.savelib.crbin.CRBinDeserializer;
import finalforeach.cosmicreach.savelib.crbin.CRBinSerializer;
import io.github.puzzle.cosmic.impl.data.point.AbstractDataPoint;

public class StringArrayDataPoint extends AbstractDataPoint<String[]> {

    public StringArrayDataPoint() {
        super(String[].class);
    }

    public StringArrayDataPoint(String[] value) {
        super(String[].class, value);
    }

    @Override
    public void read(CRBinDeserializer deserializer) {
        setValue(deserializer.readStringArray("v"));
    }

    @Override
    public void write(CRBinSerializer serializer) {
        serializer.writeStringArray("v", value);
    }
}
